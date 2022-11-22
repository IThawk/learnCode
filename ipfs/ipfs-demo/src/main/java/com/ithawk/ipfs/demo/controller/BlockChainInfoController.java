package com.ithawk.ipfs.demo.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.*;
import io.micrometer.core.instrument.Clock;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description 获取区块链信息
 * @author newonexd
 * @date 2022/6/22 21:14
 */
@RestController
@RequestMapping("/chainInfo")
public class BlockChainInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BlockChainInfoController.class);


    @Autowired
    private Web3j web3j;

    /**
     * @description 获取最新的区块号
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * * @return BigInteger
     */
    @GetMapping("/blockNumber")
    public BigInteger doGetLatestBlockNumber()throws Exception{
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();
        logger.info("BlockNumber: {}",blockNumber);
        return blockNumber;
    }

    /**
     * @description 获取所有账户
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * * @return List<String>
     */
    @GetMapping("/accounts")
    public List<String> doGetAllAccounts()throws Exception{
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        List<String> accounts = ethAccounts.getAccounts();
        logger.info("Accounts: {}",accounts);
        return accounts;
    }

    /**
     * @description 获取所有账户
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * * @return List<String>
     */
    @GetMapping("/account")
    public List<String> doOperatelAccount()throws Exception{
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        List<String> accounts = ethAccounts.getAccounts();
        logger.info("Accounts: {}",accounts);
        return accounts;
    }

    /**
     * @description 获取Gas价格
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * * @return BigInteger
     */
    @GetMapping("/gasPrice")
    public BigInteger doGetEthGasPrice()throws Exception{
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        BigInteger gasPrice = ethGasPrice.getGasPrice();
        logger.info("Ethereum Gas Price: {}",gasPrice);
        return gasPrice;
    }

    /**
     * @description 获取链Id
     * @author newonexd
     * @date 2022/6/22 21:12
     *
     * * @return BigInteger
     */
    @GetMapping("/chainId")
    public BigInteger doGetChainId()throws Exception{
        EthChainId ethChainId = web3j.ethChainId().sendAsync().get();
        BigInteger chainId = ethChainId.getChainId();
        logger.info("Ethereum Chain Id: {}",chainId);
        return chainId;
    }


    /**
     * @description 获取CoinBase
     * @author newonexd
     * @date 2022/6/22 21:12
     *
     * * @return String
     */
    @GetMapping("/coinbase")
    public String doGetCoinBase()throws Exception{
        EthCoinbase ethCoinbase = web3j.ethCoinbase().sendAsync().get();
        String coinBase = ethCoinbase.getAddress();
        logger.info("Ethereum CoinBase Address: {}",coinBase);
        return coinBase;
    }


    /**
     * @description 根据区块号获取区块信息
     * @author newonexd
     * @date 2022/6/22 21:12
     * @param blockNumber  区块号
     * @return String
     */
    @GetMapping("/getBlockInfo")
    public String doGetAll(@RequestParam(value="blockNumber")Long blockNumber)throws Exception{
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber,true).sendAsync().get();
        EthBlock.Block block = ethBlock.getBlock();
        Gson gson = new Gson();
        String info = gson.toJson(block);
        logger.info(info);
        return info;
    }


    /**
     * @description 根据区块号获取所有交易
     * @author newonexd
     * @date 2022/6/22 21:13
     * @param blockNumber 区块号
     * @return String
     */
    @GetMapping("/getTransactionByBlockNumber")
    public String doGetTransactionInfoByBlockNumber(@RequestParam(value="blockNumber")Long blockNumber)throws Exception{
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber,true).sendAsync().get();
        List<EthBlock.TransactionResult> transactionResults = ethBlock.getBlock().getTransactions();
        List<Transaction> txInfos = new ArrayList<>();

        transactionResults.forEach(txInfo->{
            Transaction transaction = (Transaction)txInfo;
            txInfos.add(transaction);
        });
        Gson gson = new Gson();
        String transactionInfo = gson.toJson(txInfos);
        logger.info(transactionInfo);
        return transactionInfo;
    }


    /**
     * @description 根据交易哈希值获取交易信息
     * @author newonexd
     * @date 2022/6/22 21:13
     * @param txHash 交易哈希值
     * @return String
     */
    @GetMapping("/getTransactionInfoByHash")
    public String doGetTransactionInfoByHash(@RequestParam(value="txHash")String txHash)throws Exception{
        EthTransaction transaction = web3j.ethGetTransactionByHash(txHash).sendAsync().get();
        Optional<Transaction> optionalTransaction = transaction.getTransaction();
        StringBuilder txInfo = new StringBuilder();
        if(optionalTransaction.isPresent()){
            Transaction transactionInfo = optionalTransaction.get();
            Gson gson = new Gson();
            txInfo.append(gson.toJson(transactionInfo));
        }
        logger.info(txInfo.toString());
        return txInfo.toString();
    }
}

