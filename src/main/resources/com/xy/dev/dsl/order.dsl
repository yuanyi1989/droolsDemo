[condition] 下单时间超过{hour}点=Order(this==$order,status == OrderStatus.CREATED,date.hours > {hour})
[condition] 如果有新创建订单=$order : Order(status == OrderStatus.CREATED)
[condition] 如果有已支付订单=$order : Order(status == OrderStatus.PAYED)
[condition] 并且=&&
[condition] 订单状态为{status}=$order : Order(status == OrderStatus.messageOf("{status}"))

[consequence] 订单价格增加{price}=$order.finalPrice = $order.getFinalPrice()+{price}
[consequence] 订单价格减少{price}=$order.finalPrice = $order.getFinalPrice()-{price}
[consequence] 更新订单状态为{status}=$order.status = OrderStatus.messageOf("{status}")
[consequence] 订单价格=$order.finalPrice
[consequence] 打印#{content}=System.out.println({content})
[consequence] 打印字符串#{content}=System.out.println("{content}")
[consequence] 保存订单=update($order)
[consequence] 订单用户距离价格因素=$order.user.distance
[consequence] 发送邮件{content}=sendSMS($order.user.phone, "{content}")