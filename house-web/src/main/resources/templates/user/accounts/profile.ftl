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
                        <li><a href="#">Account</a></li>
                        <li class="active">Profile</li>
                    </ol>
                </div>
                <#--  End BreadCrumb  -->
                <div class="container">
                    <div class="row">
                        <#--    -->
                        <div class="col-md-3 col-sm-2">
                            <section id="sidebar">
                                <header><h3>账号</h3></header>
                                <aside>
                                    <ul>
                                        <li class="active"><a href="/accounts/profile"><i class="fa fa-user"></i><span>个人信息</span></a></li>
                                        <li><a href="/house/ownlist"><i class="fa fa-home"></i><span>我的房产信息</span></a></li>
                                        <li><a href="/house/bookmarked"><i class="fa fa-heart"></i><span>房产收藏</span></a></li>
                                    </ul>
                                </aside>
                            </section><#--  ./sidebar end  -->
                        </div><#--  col-md-3 end  -->
                        <#--  个人信息  -->
                        <div class="col-md-9 col-sm-10">
                            <section id="profile">
                                <header><h1>个人信息</h1></header>
                                <div class="row">
                                    <div class="col-md-3 col-sm-3">
                                        <img class="image" src="${(loginUser.avatar)!}" />
                                    </div>
                                    <div class="col-md-9 col-sm-9">
                                        <form role="form" id="form-account-profile" method="post" action="/accounts/profile" >
                                            <input type="hidden" value="${(loginUser.email)!}" name="email" />
                                            <section id="contact">
                                                <h3>联系方式</h3>
                                                <dl>
                                                    <#--  姓名  -->
                                                    <dt><label for="form-accont-name">Your Name:</label></dt>
                                                    <dd>
                                                        <div class="form-group">
                                                            <input type="text" class="form-control" id="form-account-name" name="name" required value="${(loginUser.name)!}" />
                                                        </div>
                                                    </dd>
                                                    <#--  电话  -->
                                                    <dt><label for="form-account-phone">Your Phone:<label></dt>
                                                    <dd>
                                                        <div class="form-group">
                                                            <input type="text" class="form-control" id="form-account-phone" name="phone" required value="${(loginUser.phone)!}" />
                                                        </div>
                                                    </dd>
                                                    <#--  邮件  -->
                                                    <dt><label for="form-account-email">Your Email:</label></dt>
                                                    <dd>
                                                        <div class="form-group">
                                                            <input type="text" disabled class="form-control" id="form-account-email" name="email" required value="${(loginUser.email)!}" />
                                                        </div>
                                                    </dd>
                                                </dl>
                                            </section>
                                            <section id="aboutme">
                                                <h3>About Me</h3>
                                                <div class="form-group">
                                                    <textarea class="form-control" id="form-account-agent-message" rows="5" name="aboutme">${(loginUser.aboutme)!}</textarea>
                                                </div>
                                            </section>
                                            <section id="social">
                                                <div class="form-group clearfix">
                                                    <button type="submit" class="btn pull-right btn-default" id="account-submit">更新</button>
                                                </div>
                                            </section>
                                        </form>

                                        <#--  Change Password  -->
                                        <section id="change-password">
                                            <header><h2>更新密码</h2></header>
                                            <div class="row">
                                                <div class="col-md-6 col-sm-6">
                                                    <form role="form" id="form-account-password" method="POST" action="/accounts/changePassword">
                                                        <input type="hidden" value="${(loginUser.email)!}" name="email"/>
                                                        <div class="form-group">
                                                            <label for="form-account-password-current">当前密码:</label>
                                                            <input type="password" class="from-control" name="password" id="form-account-password-current" />
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="form-account-password-new">新密码:</label>
                                                            <input type="password" class="from-control" name="newPasword" id="form-account-password-new" />
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="form-account-password-confirm">确认密码:</label>
                                                            <input type="password" class="from-control" name="confirmPassword" id="form-account-password-confirm" />
                                                        </div>
                                                        <div class="form-group clearfix">
                                                            <button type="submit" class="btn btn-default" id="form-account-password-submit">更新密码</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                </div><#--  ./container  -->
            </div> 
            <#--  End Page Content  -->   
            <#--  Page Footer  -->
            <@common.footer />
            <#--  End Page Footer  -->
        </div>
        <@common.js/>
        <!--[if gt IE 8]>
        <script type="text/javascript" src="/assets/js/ie.js"></script>
        <![endif]-->
        <script  type="text/javascript" >
            $(document).ready(function() {
                var errorMsg   = "${errorMsg!""}";
                var successMsg = "${successMsg!""}";
                if(errorMsg){ 
                    errormsg("error",errorMsg);
                }
                if(successMsg) {
                    successmsg("success",successMsg);
                }
                })
        </script>
    </body>
</html>