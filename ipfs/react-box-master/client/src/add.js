import React, { Component } from "react";
import SimpleStorage from './contracts/SimpleStorage' // step1
import truffleContract from 'truffle-contract' //step2
//import getWeb3 from './utils/getWeb3'
import Web3 from 'web3';//导入web3库
import ipfsAPI from 'ipfs-api'
import "./App.css";
const ipfs = ipfsAPI('localhost', '5001', { protocol: 'http' })
//leaving out the arguments will default to these values
let saveImageOnIpfs = (reader) => {
    return new Promise(function (resolve, reject) {
        const buffer = Buffer.from(reader.result);
        ipfs.add(buffer).then((response) => {
            console.log(response)
            resolve(response[0].hash);
        }).catch((err) => {
            console.error(err)
            reject(err);
        })
    })
}
class App extends Component {
    constructor() {
        super()
        this.state = {
            web3: '',
            contractInstance: '',
            hash: '',
            writeOK: false,
            response: '',
        }
    }
    async componentWillMount() {
        //1. web3
        //2. contract实例化
        //let web3 = await getWeb3()
        let web3=window.web3 
        const contract = truffleContract(SimpleStorage) //step3
        contract.setProvider(web3.currentProvider) //step4
        const contractInstance = await contract.deployed() //step5
        this.setState({
            web3,
            contractInstance,
        }
        )
    }
    upload = async (info) => {
        console.log('info :', info)
        let reader = new FileReader()
        reader.readAsArrayBuffer(info)
        console.log('111reader:', reader)
        //在上传结束后， reader⾥⾯就是图⽚的数据
        reader.onloadend = () => {
            console.log('111:', reader)
            console.log('222:', reader.result)
            //上传到ipfs //TODO
            saveImageOnIpfs(reader).then(hash => {
                console.log('333:', hash)
                this.setState({ hash })
            })
        }
    }
    saveHashToEth = async () => {
        let { contractInstance, hash, web3 } = this.state
        try {
            let accounts = await web3.eth.getAccounts()
            let res = await contractInstance.set(hash, {
                from:
                    accounts[0]
            })
            // console.log({txHash: res.txHash.receipt})
            console.log('writeOK:', true)
            this.setState({ writeOK: true })
        } catch (e) {
            console.log(e)
            this.setState({ writeOK: false })
            console.log('writeOK :', false)
        }
    }
    getHash = async () => {
        let { contractInstance } = this.state
        try {
            // let accounts = await web3.eth.getAccounts()
            let hash = await contractInstance.get()
            this.setState({ response: hash })
            console.log(hash)
        } catch (e) {
            console.log(e)
        }
    }
    render() {
        let { contractInstance, hash, writeOK, response } = this.state
        return (
            <div>
                <h1>合约的地址为: {contractInstance.address}</h1>
                <h2>上传图⽚到ipfs</h2>
                <div>
                    <label id="file">请选择上传的⽂件</label>
                    <input type="file" ref="fileid" id="file"
                        name="file" multiple="multiple" />
                    <button onClick={async () =>
                        this.upload(this.refs.fileid.files[0])}>点击我上传</button>
                    {
                        hash && <h2>图⽚已上传到ipfs: {hash}</h2>
                    }
                    {
                        hash && <button onClick={async () =>
                            this.saveHashToEth()}>点击我上传到区块链</button>
                    }
                    {
                        writeOK && <h2>数据已上传到区块链!<br /></h2>
                        && <button onClick={async () =>
                            this.getHash()}>点击获取图⽚内容</button>
                    }
                    {
                        response && <h2>返回的哈希值为: {response}</h2>
                    }
                    {
                        response &&
                        <div>
                            浏览器访问结果:
                            {"http://localhost:8888/ipfs/" + response}
                            <img src={"http://localhost:8888/ipfs/" +
                                response} />
                        </div>
                    }
                </div>
            </div>
        )
    }
}

export default App;