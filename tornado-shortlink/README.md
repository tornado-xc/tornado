## 短链服务

用于将长连接转换为短链接，以解决在实际项目中长链难以处理问题，如：

* 短信通知：由于短信长度限制为70个字符，如果链接过长会导致产生多条短信，从而提高成本；再一个长链过长会导致主体信息不够明显
* 二维码难以识别：如果将长链直接写入到二维码中会导致二维码过密难以识别等问题
* 提高安全性：隐藏真实服务地址
* 方便统计等

#### 1、数据库设计

* 提供链接关系映射表用于存储短链code与长链之间的映射关系

* SQL

  ```sql
  ```

  
