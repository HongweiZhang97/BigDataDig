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

    <title></title>
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
    <style>
        body {
            background: url("yw.jpg") no-repeat center/cover;
        }

        .fun {
            padding-top: 150px;
        }
    </style>
</head>
  <body>
      <nav class="navbar navbar-default navbar-fixed-top"
           style="background-color: #f6f6f6; border: none;box-shadow: 0px 2px 8px 0px rgba(50,50,50,0.25);" role="navigation">
          <div class="container">
              <div class="navbar-header">
                  <a class="navbar-brand">
                      BP算法应用：鸢尾花种类判断
                  </a>
              </div>
              <div class="col-xs-3">
                  <ul class="nav nav-pills nav-stacked">
                      <li><a href="backMainPageAction">&nbsp;&nbsp;判断花的种类 </a></li>
                      <li class="active"><a href="Custom.jsp">&nbsp;&nbsp;自定义网络 </a></li>
                      <li><a href="Login.jsp">&nbsp;&nbsp;返回登录 </a></li>
                  </ul>
              </div>
          </div>
      </nav>
      <form action="customAction.action" method="post">
          <div class="fun" style="width: 600px;margin-left:250px">
              <div class="input-group">
                  <div class="input-group-addon"></div>
                  <input type="text" name="hidNum" class="form-control" placeholder="隐层节点数" />

              </div>
              <br />
              <div class="input-group">
                  <div class="input-group-addon"></div>
                  <input type="text" name="trainNum" class="form-control" placeholder="最大训练迭代次数" />

              </div>
              <br />
              <div class="input-group">
                  <div class="input-group-addon"></div>
                  <input type="text" name="learningRate" class="form-control" placeholder="学习率" />

              </div>
              <br />
              <div class="input-group">
                  <div class="input-group-addon"></div>
                  <input type="text" name="momentum" class="form-control" placeholder="动量因子" />

              </div>
              <br />
              <div class="input-group">
                  <div class="input-group-addon"></div>
                  <input type="text" name="desiredTrainAcc" class="form-control" placeholder="最小训练精度" />

              </div>
              <br />
              <div class="input-group">
                  <div class="input-group-addon"></div>
                  <input type="text" name="desiredTrainMistake" class="form-control" placeholder="最大训练误差" />

              </div>
              <br />
              <input type="submit" class="form-control btn btn-success" />
              <br /><br />
          </div>
      </form>
  </body>
</html>