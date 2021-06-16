mac地址生成工具

**功能：**

1. 生成16进制mac列表
    - 输入mac首地址 和 mac尾地址来生成一个mac地址列表，并导出到excel。
    - mac一般12位，工具设置最大到32位
    - mac：8850f6100690、8850f61006be
    
2. 生成华为四元组文件
    - 输入productId、cid，选择从华为生成的三元组秘钥文件，生成四元组txt。

**TODO**

1. ~~需要限制一下生成mac地址的条数，几万条之内还好，百万千万的话可能卡死。√~~
2. 十六进制自增算子需要优化，比较笨重。
    - 当前是把十六进制转为十进制后再进行比较，比较时分为参与计算和不参与计算(本轮循环中不变的值)两部分，比较后再转为十六进制，还要考虑是否进位，最后再将未参与计算的部分 和参与计算的部分进行拼接。
3. ~~打成可执行的jar~~
4. ~~prodId等文本框设置输入内容格式，比如只支持数字和字母。`Pattern.matches("[0-9a-zA-Z]+", prodId);`~~
5. 待发现

