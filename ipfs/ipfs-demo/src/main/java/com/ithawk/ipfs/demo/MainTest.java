package com.ithawk.ipfs.demo;

import java.math.BigDecimal;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

//import com.mao.utils.Consts;

public class MainTest {

    public static void main(String[] argv) throws Exception {
        // 1.连接到以太坊网络上节点
        // 默认参数为http://localhost:8545
        // 下面两个语句等同
        // Web3j web3j = Web3j.build(new HttpService());
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
        // 2.加载以太坊钱包 参数说明： 密码 账户文件路径
        // loadCredentials(String password, String source)
        Credentials credentials = WalletUtils.loadCredentials("Consts.PASSWORD"," Consts.PATH");
        // 3.创建交易 参数列表： 网络 钱包 接收方地址 数值 单位
        // sendFunds(Web3j web3j, Credentials credentials, String toAddress, BigDecimal
        // value, Unit unit)
        TransactionReceipt transferReceipt = Transfer.sendFunds(web3j, credentials,
                "0x118C56BAADdC4DA63aDD3B4bcf241681608ead71", BigDecimal.ONE, Convert.Unit.WEI).send();
        // 4.输出交易的Hash值
        System.out.println(transferReceipt.getTransactionHash());
    }
}