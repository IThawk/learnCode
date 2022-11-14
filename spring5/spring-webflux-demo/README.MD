## 00-labmda
### function
####  Predicate 接口
    该接口用于判断输入的对象是否符合某个条件。该接口中只有一个抽象方法 test()， 三 个默认方法 and（与）、or（或）、negate（非），还有一个静态方法 isEqual()。
####  Consumer 接口
    Consumer，消费者，只有一个输入没有输出的函数接口。该接口有一个抽象方法accept()，与一个默认方法 andThen()。
####  Supplier 接口
    Supplier，提供者，没有输入但有输出的函数接口。该接口只有一个抽象方法 get()，用于获取函数接口方法的返回值。
####  Function 接口
    只有一个输入，且有一个输出的函数接口。该接口中有一个抽象方法 apply()，有两个默认方法 andThen()与 compose()，及一个静态方法 identity()
#### UnaryOperator 接口
    只有一个输入，且有一个输出的函数接口。该接口中有一个抽象方法 apply()，有两个默认方法 andThen()与 compose()，及一个静态方法 identity()
####  BiFunction 接口
    有两个输入与一个输出的函数接口。BiFunction 接口与 Function 接口没有任何关系。其有一个抽象方法 apply()，与一个默认方法 andThen()。这两个方法的意义与前面的相同。
####  BinaryOperator 接口
