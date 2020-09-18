package com.gupaoedu.vip.netty.pattern;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

/**
 * Created by Tom.
 */
public class IterableTest {

//    public static void main(String[] args) {
//        ByteBuf a = Unpooled.wrappedBuffer(new byte[]{1,2,3});
//
//        a.forEachByte(new ByteProcessor() {
//            public boolean process(byte b) throws Exception {
//                System.out.println(b);
//                return true;
//            }
//        });
//    }


    public static void main(String[] args) {
       ByteBuf a = Unpooled.wrappedBuffer(new byte[]{1,2,3});
       ByteBuf b = Unpooled.wrappedBuffer(new byte[]{4,5,6});

       ByteBuf merge = merge(a,b);
       merge.forEachByte(new ByteProcessor() {
           public boolean process(byte b) throws Exception {
               System.out.println(b);
               return true;
           }
       });
    }

//    public static ByteBuf merge(ByteBuf a,ByteBuf b){
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
//        byteBuf.writeBytes(a);
//        byteBuf.writeBytes(b);
//        return  byteBuf;
//    }


    //零拷贝
    public static ByteBuf merge(ByteBuf a,ByteBuf b){
        CompositeByteBuf byteBuf = ByteBufAllocator.DEFAULT.compositeBuffer(2);
        byteBuf.addComponent(true,a);
        byteBuf.addComponent(true,b);
        return  byteBuf;

    }
}
