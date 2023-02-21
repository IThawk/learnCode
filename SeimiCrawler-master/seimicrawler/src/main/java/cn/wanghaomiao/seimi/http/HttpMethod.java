/*
   Copyright 2015 Wang Haomiao<seimimaster@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package cn.wanghaomiao.seimi.http;


/**
 * @author github.com/zhegexiaohuozi seimimaster@gmail.com
 *         Date:  14-7-7.
 */
public enum HttpMethod {
    GET("get"),POST("post");
    private String val;
    HttpMethod(String val){
        this.val = val;
    }
    public String val(){
        return this.val;
    }
}
