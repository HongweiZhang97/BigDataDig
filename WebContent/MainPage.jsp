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
                      <li class="active"><a href="backMainPageAction">&nbsp;&nbsp;判断花的种类 </a></li>
                      <li><a href="Custom.jsp">&nbsp;&nbsp;自定义网络 </a></li>
                      <li><a href="Login.jsp">&nbsp;&nbsp;返回登录 </a></li>
                  </ul>
              </div>
          </div>
      </nav>
      <table>
          <tr>
              <td>
                  <form action="anticipateAction.action" method="post">
                      <div class="fun" style="width: 400px;margin-left:80px">
                          <div class="input-group">
                              <div class="input-group-addon"></div>
                              <input type="text" id="e_width" name="param1" class="form-control" placeholder="萼片宽度（cm）" />

                          </div>
                          <br />
                          <div class="input-group">
                              <div class="input-group-addon"></div>
                              <input type="text" id="e_length" name="param2" class="form-control" placeholder="萼片长度（cm）" />

                          </div>
                          <br />
                          <div class="input-group">
                              <div class="input-group-addon"></div>
                              <input type="text" id="f_width" name="param3" class="form-control" placeholder="花瓣宽度（cm）" />

                          </div>
                          <br />
                          <div class="input-group">
                              <div class="input-group-addon"></div>
                              <input type="text" id="f_length" name="param4" class="form-control" placeholder="花瓣长度（cm）" />

                          </div>
                          <br />
                          <input type="submit" class="form-control btn btn-success" />
                          <br /><br />
                      </div>
                  </form>
              </td>
              <td>
                  <div class="fun" style="width: 600px;margin-left:100px">
                      <label>当前预测网络参数</label><br />
                      <label>网络结构：4-</label><s:property value="param0" /><label>-2</label><br />
                      <label>网络训练参数</label><br />
                      <label>网络最大训练次数：</label><s:property value="param1" /><br />
                      <label>学习率：</label><s:property value="param2" /><br />
                      <label>动量项：</label><s:property value="param3" /><br />
                      <label>训练精度要求：</label><s:property value="param4" /><br />
                      <label>测试误差要求：</label><s:property value="param5" /><br />
                      <label>使用批量学习：</label><s:property value="param6" /><br />
                      <label>网络性能</label><br />
                      <label>训练精度:</label><s:property value="param7" /><br />
                      <label>训练误差:</label><s:property value="param8" /><br />
                      <label>测试精度:</label><s:property value="param9" /><br />
                      <label>测试误差:</label><s:property value="param10" /><br />
                  </div>
              </td>
          </tr>
      </table>
  </body>
</html>