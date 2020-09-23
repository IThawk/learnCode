package com.ithawk.netty.demo.rpc.provider;

import com.ithawk.netty.demo.rpc.IRpcService;

public class RpcServiceImpl implements IRpcService {

	public int add(int a, int b) {
		return a + b;
	}

	public int sub(int a, int b) {
		return a - b;
	}

	public int mult(int a, int b) {
		return a * b;
	}

	public int div(int a, int b) {
		return a / b;
	}

}
