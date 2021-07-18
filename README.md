mac地址生成工具

#### 2.0

![2.0需求.png](/src/resources/static/image/2.0需求.png)


#### 1.0

![MacGenerator.png](/src/resources/static/image/MacGenerator.png)

1. 生成mac地址列表功能
    - mac起始地址、mac结束地址：不能为空；输入内容应为十六进制字符（0-9a-fA-F），工具会将大写转为小写，最终以小写字母展示；限制长度最大32位。
    - prodId：不能为空；限制输入内容只能为数字或字母（0-9a-zA-Z），工具不会将prodId中的字符进行大小写转换；限制长度最大64位。
    - 工具默认支持生成最大65536条记录，即：***0000 - ***ffff。原因：
        - ① 防止一次过多（百万千万）导致工具卡死。
        - ② excel2010每个sheet最多支持1048576行（打开excel，ctrl+↓可以定位到最后一行查看行号），低版本可能更少，开放十六进制倒数第五位的话，最多会生成16的5次方等于1,048,576条再加上第一行目录，会造成写入excel失败。为保持兼容，暂设置65536条。
        - ③ 一次六万多条记录应该能满足需求。如若不满足可再找作者修改。
    - 生成的mac地址列表excel默认保存在桌面，名称为“三元组申请表(工具生成).xlsx”或者“三元组申请表(工具生成) +当前时间戳.xlsx”
    - 导出的mac地址列表excel中单元格为文本格式。

    
2. 生成华为四元组功能
    - prodId：不能为空；限制输入内容只能为数字或字母（0-9a-zA-Z），工具不会将prodId中的字符进行大小写转换；限制长度最大64位。
    - cid：不能为空；限制输入内容只能为数字或字母（0-9a-zA-Z），工具不会将cid中的字符进行大小写转换；限制长度最大64位。
    - 三元组文件：只支持后缀为.xls和.xlsx的excel文件，格式如下，工具会读取第二行，第二列和第三列的内容，使用时请保证格式正确！
        
        ![三元组.png](/src/resources/static/image/三元组.png)
        
    - 生成的四元组默认保存在跟选择的三元组相同的目录下，名称为“华为四元组.txt”或者“华为四元组+当前时间戳.txt”。
    - Txt文本中内容间以tab键分割。


