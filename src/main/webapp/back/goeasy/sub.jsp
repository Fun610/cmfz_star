<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Document</title>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        var goEasy = new GoEasy({
        //接收的appkey
            host:"rest-hangzhou.goeasy.io",
            appkey: "BC-74e8ef1ccfff4845a701da800691c786",
            userId:"user-test"
        });
        goEasy.subscribe({
            //当前的channel名称
            channel: "channel001",
            onMessage: function (message) {
                alert("Channel:" + message.channel + " content:" + message.content);

            }
        });
    </script>
</head>
<body>

</body>
</html>