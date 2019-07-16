var secKillObject={
    url:{
        getSystemTime:function () {
            return "/getSystemTime"
        },
        getGoodsRandomNameById:function (goodsId) {
            return "/getGoodsRandomNameById/"+goodsId
        },
        seckill:function (goodsId, goodsRandomName) {
            return "/seckill/"+goodsId+"/"+goodsRandomName
        }
    },
    fun:{
        initSecKill:function (goodsId, startTime, endTime) {
            $.ajax({
                url:secKillObject.url.getSystemTime(),
                type:"get",
                success:function (data) {
                    if (data.code != '0') {
                        alert(data.errorMessage)
                        return false
                    }
                    var date = data.data
                    if (date < startTime) {
                        secKillObject.fun.secKillCountDown(startTime*1)
                        return false
                    }
                    if (date > endTime) {
                        $("#seckillCountDown").html('<span style="color: red">秒杀已结束</span>')
                        return false
                    }
                    $("#secKillBtn").bind("click",function () {
                        $(this).attr("disabled",true)
                        secKillObject.fun.doSeckill(goodsId)
                    })
                },
                error:function () {
                    alert("网络错误请稍后重试")
                }
            })
        },
        secKillCountDown:function (startTime) {
            //使用jquery的倒计时插件实现倒计时
            /* + 1000 防止时间偏移 这个没有太多意义，因为我们并不知道客户端和服务器的时间偏移
            这个插件简单了解，实际项目不会以客户端时间作为倒计时的，所以我们在服务器端还需要验证*/
            var killTime = new Date(startTime);
            $("#seckillCountDown").countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('距秒杀开始还有: %D天 %H时 %M分 %S秒');
                $("#seckillCountDown").html("<span style='color:red;'>"+format+"</span>");
            }).on('finish.countdown', function () {
                //倒计时结束后回调事件，已经开始秒杀，用户可以进行秒杀了，有两种方式：
                //1、刷新当前页面
                location.reload();
                //或者2、调用秒杀开始的函数
            });
        },
        doSeckill:function (goodsId) {
            $.ajax({
                url:secKillObject.url.getGoodsRandomNameById(goodsId),
                type:"get",
                dataType:"json",
                success:function (data) {
                    if (data.code != '0') {
                        alert(data.errorMessage)
                        return false
                    }
                    secKillObject.fun.seckill(goodsId,data.data)
                },
                error:function () {
                    alert("网络错误请稍后重试")
                }
            })
        },
        seckill:function (goodsId, goodsRandomName) {
            $.ajax({
                url:secKillObject.url.seckill(goodsId,goodsRandomName),
                type:"get",
                dataType:"json",
                success:function (data) {
                    if (data.code != '0') {
                        alert(data.errorMessage)
                        return false
                    }

                },
                error:function () {
                    alert("网络错误请稍后重试")
                }
            })
        }
    }
}