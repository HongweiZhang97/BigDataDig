<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用户登录</title>
    <meta charset="utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="Content/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <style type="text/css">
        .errorMessage li {
            list-style-type: none;
        }

        .errorMessage {
            padding: 0;
        }

        body {
            background: url("login.jpg") no-repeat center/cover;
        }

        .fun {
            padding-top: 50px;
        }
    </style>
</head>
  
<body>
    <center>
        <h1>BP算法应用：鸢尾花种类判断</h1>
        <h2>用户登录</h2>
        <form action="loginAction.action" method="post">
            <table class="wwFormTable">
                <div class="fun" style="width: 400px">
                    <div class="input-group">
                        <div class="input-group-addon"></div>
                        <input type="text" id="loginAction_username" name="username" class="form-control" placeholder="用户名" />

                    </div>
                    <br />
                    <div class="input-group">
                        <div class="input-group-addon"></div>
                        <input type="password" id="loginAction_password" name="password" class="form-control" placeholder="密码" />

                    </div>
                    <br />
                    <div class="input-group">
                        <div class="input-group-addon"><img src="Security/SecurityCodeImageAction" id="Verify" style="cursor:hand;vertical-align:middle" alt="看不清，换一张" /></div>
                        <input type="text" name="securityCode" style="vertical-align:middle" class="form-control" placeholder="验证码" />

                    </div>
                    <br />
                    <div>
                        <div class="input-group-addon"><input type="submit" id="loginAction_submit" name="submit" value="登录" class="form-control btn btn-success" />&nbsp;</div>
                        <input type="reset" class="form-control btn btn-success" value="重置" />
                    </div>
                    <br />
                    <p style="text-align: right"><a href="Register.jsp">注册</a></p>
                </div>
                <s:fielderror fieldName="username"></s:fielderror>
                <s:fielderror fieldName="password"></s:fielderror>
                <s:fielderror fieldName="securityCode"></s:fielderror>
            </table>
        </form>
    </center>
</body>
</html>
