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
                        <li class="active">创建账号</li>
                    </ol>
                </div>
                <#--  End BreadCrumb  -->
                <div class="container">
                    <div class="row">
                        <#--  重置密码  -->
                        <div class="col-md-12 col-sm-12">
                            <section id="aboutme">
                                <header><h1>重置密码</h1></header>
                                <section id="ceo-section" class="center">
                                    <div class="cite no-bottom-margin">已经向邮箱${email}发送重置密码确认邮件！</div>
                                </section>
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