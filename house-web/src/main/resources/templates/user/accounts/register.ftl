<!DOCTYPE html>
<html lang="en">
<#--  引入头部公共文件  -->
<@common.header/>
<body class="page-sub-page page-create-account page-account" id="page-top">
<div class="wrapper">
<#--  Navigation  -->
<@common.nav />
<#--  Page content  -->
    <div id="page-content">
    <#--  Breadcrumb  -->
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li class="active">注册</li>
            </ol>
        </div>
    <#--  end BreadCrumb  -->
        <div class="container">
            <header><h1>注册账号</h1></header>
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <h3>Account Type</h3>
                    <form role="form" id="form-create-account" method="post" enctype="multipart/form-data"
                          action="/accounts/register">
                        <div class="radio" id="create-account-user">
                            <label>
                                <input type="radio" value="1" id="account-type-user" name="type" required/>普通用户
                            </label>
                        </div>
                        <div class="radio" id="agent-switch" data-agent-state="">
                            <label>
                                <input type="radio" value="2" id="account-type-agent" name="type" required/>经纪人
                            </label>
                        </div>
                        <div id="agency" class="disabled">
                            <div class="form-group">
                                <label for="account-agency">选择经纪机构</label>
                                <select name="agencyId" id="agencyId">
                                    <option value="0">请选择经纪机构</option>
                                <#list agencyList as agency>
                                    <option value="${agency.id}">${agency.name}</option>
                                </#list>
                                </select>
                            </div><#--  /.form-group  -->
                        </div>
                        <hr>
                    <#--  全名  -->
                        <div class="form-group">
                            <label for="form-create-account-full-name">全名:</label>
                            <input type="text" class="form-control" id="form-create-account-full-name" name="name"
                                   required/>
                        </div>
                    <#--  邮件  -->
                        <div class="form-group">
                            <label for="form-create-account-email">Email:</label>
                            <input type="email" class="form-control" id="form-create-account-email" name="email"
                                   required/>
                        </div>
                    <#--  手机号  -->
                        <div class="form-group">
                            <label for="form-create-account-phone">手机号:</label>
                            <input type="text" class="from-control" id="form-create-account-phone" name="phone"
                                   required/>
                        </div>
                    <#--  密码  -->
                        <div class="form-group">
                            <label for="form-create-account-passwd">密码:</label>
                            <input type="password" class="form-control" id="form-create-account-passwd" name="passwd"
                                   required/>
                        </div>
                    <#--  确认密码  -->
                        <div class="form-group">
                            <label for="form-create-account-confirm-passwd">确认密码:</label>
                            <input type="password" class="form-control" id="form-create-confirm-passwd"
                                   name="confirmPasswd" required/>
                        </div>
                    <#--  自我介绍  -->
                        <div class="form-group">
                            <label for="form-create-account-aboutme">自我介绍:</label>
                            <textarea class="form-control" id="form-create-account-aboutme" name="aboutme"></textarea>
                        </div>

                    <#--  用户图像  -->
                        <div class="form-group">
                            <label for="form-create-account-avatarFile">用户头像:</label>
                            <input id="file-upload" type="file" class="file" multiple="true"
                                   data-show-upload="false" data-show-caption="false" data-show-remove="false"
                                   accept="image/jpeg,image/png" data-browse-class="btn btn-default"
                                   data-browse-label="Browse Images" name="avatarFile" required>
                            <figure class="note"><strong>Hint:</strong> You can upload all images at once!</figure>
                        </div>

                    <#--  注册按钮  -->
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-right btn-default" id="account-submit">注册账号</button>
                        </div>
                    </form>
                    <hr>
                    <div class="center">
                        <figure class="note">我已阅读并同意<a href="terms-conditions.html">用户协议</a></figure>
                    </div>
                </div>
            </div><#--  /.row  -->
        </div><#--  /.container  -->
    </div>
<#--  end  -->
<#--  Page Footer  -->
<@common.footer/>
</div>
<#--公共JS的引入-->
<@common.js/>
<!--[if gt IE 8]>
<script type="text/javascript" src="/static/assets/js/ie.js"></script>
<![endif]-->
<script type="text/javascript">
    $(document).ready(function () {
        var errorMsg = "${errorMsg!""}";
        var successMsg = "${successMsg!""}";
        if (errorMsg) {
            errormsg("error", errorMsg);
        }
        if (successMsg) {
            successmsg("success", successMsg);
        }
    })

</script>
</body>
</html>