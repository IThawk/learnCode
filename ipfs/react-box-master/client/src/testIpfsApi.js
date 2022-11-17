const ipfsAPI = require('ipfs-api')
const ipfs = ipfsAPI('localhost', '5001', { protocol: 'http' })
//leaving out the arguments will default to these values
//add, cat, ls,
let test = async () => {
    //add
    let res = await ipfs.add(Buffer.from('helloworld!!!'))
    console.log("add result:"+res)
    let hash1 = res[0].hash
    console.log('hash1 :', hash1)
    //cat
    res = await ipfs.cat(hash1)
    console.log("content:"+res.toString())
    //ls
    res = await ipfs.ls('QmP7WvKiHdgRUx25d5dKJec7cea7zj3EYWZWauJMjLHKAe')
    console.log("ls result:"+res)    
    res.forEach(async (folder) => {
        console.log("--folder---:"+folder)
    })
}
test()