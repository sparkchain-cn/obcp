/*
SQLyog Ultimate
MySQL - 5.7.22 : Database - obcp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`obcp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `obcp`;

/*Table structure for table `bc_company` */

CREATE TABLE `bc_company` (
  `id` bigint(80) NOT NULL,
  `supervisor` bigint(80) DEFAULT NULL COMMENT '负责人ID',
  `companyNo` varchar(100) DEFAULT NULL COMMENT '公司编码',
  `companyName` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `companyAddr` varchar(500) DEFAULT NULL COMMENT '公司地址',
  `companyTel` varbinary(50) DEFAULT NULL COMMENT '公司联系电话',
  `taxid` varchar(100) DEFAULT NULL COMMENT '税号编码',
  `authFile` varchar(300) DEFAULT NULL COMMENT '授权文件地址',
  `businessLicenseFile` varchar(300) DEFAULT NULL COMMENT '营业执照文件地址',
  `corporationName` varchar(50) DEFAULT NULL COMMENT '法人姓名',
  `corporationCardnum` varchar(50) DEFAULT NULL COMMENT '法人身份证',
  `status` tinyint(4) NOT NULL COMMENT '状态:-1删除,0已审核,1:审核中,2:审核失败',
  `reviewMessage` varchar(500) DEFAULT NULL COMMENT '驳回消息',
  `verifyTime` datetime DEFAULT NULL COMMENT '审核确认时间',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `supervisor` (`supervisor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司相关信息表';

/*Data for the table `bc_company` */

/*Table structure for table `bc_department` */

CREATE TABLE `bc_department` (
  `id` bigint(80) NOT NULL,
  `parent` bigint(80) DEFAULT NULL,
  `org_id` bigint(80) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `intro` text,
  `status` tinyint(4) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构部门信息';

/*Data for the table `bc_department` */

/*Table structure for table `bc_notify_read_time` */

CREATE TABLE `bc_notify_read_time` (
  `user_id` bigint(80) NOT NULL COMMENT '用户ID',
  `read_time` datetime NOT NULL COMMENT '阅读时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息阅读记录';

/*Data for the table `bc_notify_read_time` */

/*Table structure for table `bc_org_manager` */

CREATE TABLE `bc_org_manager` (
  `id` bigint(20) NOT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '0：正常 1：禁用，-1撤销',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构管理用户';

/*Data for the table `bc_org_manager` */

/*Table structure for table `bc_organization` */

CREATE TABLE `bc_organization` (
  `id` bigint(20) NOT NULL,
  `supervisor` bigint(80) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(80) DEFAULT NULL,
  `addr` varchar(500) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `logo` varchar(500) DEFAULT NULL,
  `license` varchar(200) DEFAULT NULL,
  `industry` varchar(500) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `intro` text,
  `creator` bigint(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构信息';

/*Data for the table `bc_organization` */

/*Table structure for table `bc_user_department` */

CREATE TABLE `bc_user_department` (
  `id` bigint(80) NOT NULL,
  `user_id` bigint(80) DEFAULT NULL,
  `dept_id` bigint(80) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关系';

/*Data for the table `bc_user_department` */

/*Table structure for table `bc_user_private_token` */

CREATE TABLE `bc_user_private_token` (
  `id` bigint(80) NOT NULL,
  `user_id` bigint(80) DEFAULT NULL,
  `scope` text,
  `token` varchar(200) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户访问凭证';

/*Data for the table `bc_user_private_token` */

/*Table structure for table `sys_config` */

CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL,
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `code` varchar(500) DEFAULT NULL COMMENT '编码',
  `groups` varchar(200) DEFAULT NULL COMMENT '分组',
  `creator` varchar(80) DEFAULT NULL COMMENT '创建人',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(80) DEFAULT NULL COMMENT '修改人',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `val` text,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_config` */

insert  into `sys_config`(`id`,`name`,`code`,`groups`,`creator`,`createtime`,`updater`,`updatetime`,`val`,`remark`) values 
(1082539549810229248,'shiro配置登录地址','com.sparkchain.shiro.login.url','user','1065065711421882368','2019-01-08 15:28:36',NULL,'2019-01-08 15:28:36','/login',''),
(1082539983333490688,'shiro认证路径','com.sparkchain.shiro.anth','user','1065065711421882368','2019-01-08 15:30:19','1065065711421882368','2019-01-08 15:30:26','/404,/403,/500,/navs','需要认证后才能访问的路径'),
(1082540666820493312,'shiro配置公共路径','com.sparkchain.shiro.anno','user','1065065711421882368','2019-01-08 15:33:02','1065065711421882368','2019-05-07 16:19:27','/v1/userextend/login,/v1/userextend/register,/v1/userextend/register/code,/v1/userextend/logout,/static/**,/admin/**,/js/**,/css/**,/images/**,/login,/signup,/forgetPwd,/v1/userextend/forgetPassword/code,/v1/userextend/forgetPassword,/privacyService,/quartz/fee/syncFreeVisitApp,/v1/chainvisit/buyrecord/activityPresent,/v1/stata/chain/commonChainTradeStata,/v0/chain/getActivecount','无需认证既可访问'),
(1082541025546731520,'shiro配置无权限跳转路径','com.sparkchain.shiro.unauthorized','user','1065065711421882368','2019-01-08 15:34:28',NULL,'2019-01-08 15:34:28','/403',''),
(1082541594076250112,'shiro用户session缓存时长','com.sparkchain.shiro.timeout','user','1065065711421882368','2019-01-08 15:36:43',NULL,'2019-01-08 15:36:43','5000',''),
(1082541737324314624,'shiro配置登录成功地址','com.sparkchain.shiro.success.url','user','1065065711421882368','2019-01-08 15:37:17',NULL,'2019-01-08 15:37:17','/index',''),
(1086150783780519936,'隐私服务条款','privacyService','main','1065065711421882368','2019-01-18 14:38:21','1065065711421882368','2019-01-18 16:20:36','<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n	<br />\n</p>\n<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n	<b><span style=\"font-family:宋体;\">火花区块链接入平台网站服务条款</span><span></span></b> \n</p>\n<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n	<b><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<b><span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">前言</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">欢迎访问火花区块链接入平台并使用我们提供的产品和服务。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">在完成注册程序或以任何方式使用火花区块链接入平台服务前，请您务必仔细阅读并透彻理解本火花区块链接入平台服务条款</span><span>(</span><span style=\"font-family:宋体;\">以下或简称“服务条款”</span><span>)</span><span style=\"font-family:宋体;\">，在确认充分理解后选择接受或不接受本服务条款；一旦您完成“同意条款并注册”或开始以其他方式使用火花区块链接入平台服务，即表明您已阅读并同意受本服务条款的约束。如您不同意本服务条款或其中任何条款约定，您应不再进行下一步或停止注册程序。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">火花区块链接入平台再次提示您审慎阅读、充分理解各条款内容，特别是限制或免除责任的相应条款，限制或免除责任条款将以加粗或其他醒目形式提示您注意。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">一、</span> </b><b><span style=\"font-family:宋体;\">签约主体以及协议范围</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\" style=\"margin-left:0cm;text-indent:0cm;\">\n	<span><span>1.1. </span></span><span style=\"font-family:宋体;\">火花区块链接入平台网站服务条款是您与烨链（上海）科技有限公司（“烨链科技”或者“我们”）就使用火花区块链接入平台网站服务（包含为您提供网站页面信息浏览、账户注册服务等）而订立的有效合约。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">火花区块链接入平台包含域名为</span><span style=\"background:yellow;\">xxxxxx</span><span style=\"font-family:宋体;\">的网站以及火花区块链接入平台客户端</span><span style=\"background:yellow;\">,</span><span style=\"font-family:宋体;background:yellow;\">如</span><span style=\"background:yellow;\">APP</span><span style=\"font-family:宋体;\">。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>1.2. </span><span style=\"font-family:宋体;\">如您使用或购买火花区块链接入平台服务，您可仍需确认具体服务对应的服务条款；请您审慎阅读、充分理解各条款内容，选择接受或不接受该服务条款。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">二、账户的注册、使用与安全</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.1. </span><span style=\"font-family:宋体;\">注册的资格</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.1.1. </span><span style=\"font-family:宋体;\">您确认，在您完成注册程序或以其他火花区块链接入平台允许的方式实际使用本服务时，<b>您应当是具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织</b>（以下或简称为“您”）。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.1.2. </span><span style=\"font-family:宋体;\">若您是未成年人或限制民事行为能力人，则您不具备前述主体资格，您及您的监护人应承担因您的不当注册行为而导致的一切后果。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.1.3. </span><span style=\"font-family:宋体;\">您还需确保您不是任何国家、国际组织或者地域实施的贸易限制、制裁或其他法律、规则限制的对象，否则您可能无法正常注册及使用火花区块链接入平台服务。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2. </span><span style=\"font-family:宋体;\">账户注册</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2.1.</span><span style=\"font-family:宋体;\">当您按照注册页面提示填写信息、阅读并同意本服务条款且完成全部注册程序后，您可获得火花区块链接入平台账户并成为火花区块链接入平台的用户。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2.2. </span><span style=\"font-family:宋体;\">您在注册时候设置或确认的账户名（“账户名称”）及您设置的密码，将在注册成功后成为您的账户（账户名称及密码合称“账户”）。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2.3. </span><span style=\"font-family:宋体;\">您设置的账户名不得违反国家法律法规、火花区块链接入平台的管理规范或容易引起您与火花区块链接入平台身份的混淆，否则您的账户可能不能注册成功或火花区块链接入平台有权经通知您后予以注销。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2.4. </span><span style=\"font-family:宋体;\">您应当按照法律法规要求或按相应页面的提示，完成实名认证，准确提供并及时更新您的账户信息，以使之真实、及时、完整和准确。如您提供的资料错误、不实、过时或不完整的，火花区块链接入平台可以向您发出询问及</span><span>/</span><span style=\"font-family:宋体;\">或要求改正的通知，您应按照火花区块链接入平台的要求配合提供或者更新相关资料。因您填写的信息、资料不真实、不及时、不完整或不准确的，您应承担您不能使用火花区块链接入平台账户（不能注册成功、或账户被冻结、注销）或您在使用过程中的后果和损失。如果您在火花区块链接入平台发布的产品或应用，或通过火花区块链平台接口实现的产品或服务接入底层区块链，您需要保证您的产品或应用的终端用户完成实名认证，以备配合国家相关部门的监管。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2.5. </span><span style=\"font-family:宋体;\">火花区块链接入平台可能会就某些产品或服务的开通，要求您提供更多的身份资料和信息，做进一步的身份认证或资格验证，您的账户只有在通过这些认证和验证之后，方可获得使用相关产品或服务的资格。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.2.6. </span><span style=\"font-family:宋体;\">通常情况下，您的火花区块链接入平台账户是您在火花区块链接入平台网站进行一切活动的唯一身份标识，除非另有约定，每一个火花区块链接入平台账户都可以在本网站独立开展活动。但在下列情形下，火花区块链接入平台有权根据自己的判断，对同一及</span><span>/</span><span style=\"font-family:宋体;\">或关联法律主体拥有的多个火花区块链接入平台进行统一处理，如根据不同火花区块链接入平台账户在注册、登录、使用中的关联信息，火花区块链接入平台判断其实际为同一用户。关联信息举例：同一证件、同一手机号、同一支付账号、同一设备、同一地址等。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3. </span><span style=\"font-family:宋体;\">账户的使用和安全</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3.1. </span><span style=\"font-family:宋体;\">您有权使用您设定的账户登录火花区块链接入平台网站，进而使用火花区块链接入平台提供的其他服务。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3.2. </span><span style=\"font-family:宋体;\">火花区块链接入平台可能会以邮件、站内信、短信或电话等方式通知您服务进展情况以及提示您进行下一步的操作。在服务过程中您应当及时登录到火花区块链接入平台账户查看和进行交易操作。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3.3. </span><span style=\"font-family:宋体;\">一个火花区块链接入平台账户仅能对应唯一的法律主体。除非有法律明文规定、司法裁定或者经火花区块链接入平台同意的情况下，您不得以任何方式转让、赠与或让他人继承您的火花区块链接入平台账户。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3.4. </span><span style=\"font-family:宋体;\">您的账户和密码由您自行设置并由您保管，您须对您的火花区块链接入平台账户和密码保密。您需确保您在每个上网时段结束时，以正确步骤离开网站。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3.5. </span><span style=\"font-family:宋体;\">如发现他人未经授权使用您的火花区块链接入平台账户和密码，您应立即通知火花区块链接入平台；火花区块链接入平台将协助您冻结账户、更改密码或进行其他安全设置；您理解火花区块链接入平台对您的请求采取行动需要合理时间，火花区块链接入平台对在采取行动前已经产生的以及由您引发的后果（包括但不限于您的任何损失）不承担任何责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>2.3.6. </span><span style=\"font-family:宋体;\">火花区块链接入平台亦会在网站服务端采取合理的技术措施保障账户的安全。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">三、账户的冻结、注销及申诉</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1. </span><span style=\"font-family:宋体;\">账户的冻结</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">您的火花区块链接入平台账户（全部或部分权限或功能）在如下情况可能被冻结（如火花区块链接入平台帐户被限制资金转出功能等），火花区块链接入平台将通过邮件、站内信、短信或电话等方式通知您：</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.1. </span><span style=\"font-family:宋体;\">基于火花区块链接入平台网站或服务运行和交易安全的需要，如您发生或可能发生破坏或试图破坏火花区块链接入平台或火花区块链接入平台关联公司公平交易环境或正常交易秩序的，或任何使用含有火花区块链接入平台或火花区块链接入平台关联公司名称、品牌且对他人有误导嫌疑或任何使用某种中英文</span><span>(</span><span style=\"font-family:宋体;\">全称或简称</span><span>)</span><span style=\"font-family:宋体;\">、数字、域名等意图表示或映射与火花区块链接入平台或其关联公司具有某种关联的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.2. </span><span style=\"font-family:宋体;\">违反本服务条款、火花区块链接入平台网站的相关规则、规范（如交易规则、管理规范）、服务说明以及其他服务协议</span><span>/</span><span style=\"font-family:宋体;\">条款的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.3. </span><span style=\"font-family:宋体;\">违反国家法律、法规、政策、法律文书的规定的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.4. </span><span style=\"font-family:宋体;\">您遭到他人投诉，</span> <span style=\"font-family:宋体;\">且对方已经提供了相关证据的，而您未按照我们的要求提供相反证据的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.5. </span><span style=\"font-family:宋体;\">火花区块链接入平台根据合理分析判断您的账户操作、收益、兑换等存在异常的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.6. </span><span style=\"font-family:宋体;\">国家有权机关要求进行的冻结的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.1.7. </span><span style=\"font-family:宋体;\">火花区块链接入平台合理判断，您发生与如上行为性质相同或产生如上类似风险的其他情况的。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.2. </span><span style=\"font-family:宋体;\">账户的注销</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.2.1. </span><span style=\"font-family:宋体;\">如果出现如上第</span><span>3.1</span><span style=\"font-family:宋体;\">条情形且情形严重的，或基于国家有权机关的要求，您的火花区块链接入平台账户（全部或部分权限或功能）将被注销，火花区块链接入平台将通过邮件、站内信、短信或电话等方式通知您。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.2.2. </span><span style=\"font-family:宋体;\">您理解并同意，<b>如您连续</b></span><b><span>12</span></b><b><span style=\"font-family:宋体;\">个月未通过账户登录过火花区块链接入平台网站，且您的账户下不存在任何未到期的服务，火花区块链接入平台有权注销您的账户，您将不能再登录火花区块链接入平台网站</span></b><span style=\"font-family:宋体;\">。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.3. </span><span style=\"font-family:宋体;\">申诉</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">发生前述账户冻结或注销情况下，您应及时予以关注并可以按照程序进行申诉等后续操作：</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.3.1. </span><span style=\"font-family:宋体;\">您通过申诉程序，向火花区块链接入平台申请解除上述冻结或注销的，为了您的账户安全，您应配合如实提供身份证明及相关资料，以及火花区块链接入平台要求的其他信息或文件，以便火花区块链接入平台进行核实。您应充分理解您的申诉并不必然被允许，火花区块链接入平台有权决定是否同意您的申诉请求。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>3.3.2. </span><span style=\"font-family:宋体;\">您理解并同意，如果您拒绝如实提供身份证明及相关资料，或未能通过火花区块链接入平台审核，火花区块链接入平台有权长期冻结该等账户且长期限制账户部分或全部功能。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">四、服务内容</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>4.1.</span><span style=\"font-family:宋体;\">用户可以购买使用火花区块链接入平台提供的各项区块链服务，具体服务内容以火花区块链接入平台实际提供为准。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>4.2.</span><span style=\"font-family:宋体;\">火花区块链接入平台提供的区块链服务，仅可以与其相应内容有关的目的而被使用，用户不得通过任何技术手段更改或删除他人发布的任何信息，火花区块链接入平台有权根据自身判断修改或删除任何不适合通过火花区块链接入平台上传、发布的信息。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>4.3. </span><span style=\"font-family:宋体;\">用户使用区块链服务时所需的相关的设备以及网络资源等（如个人电脑及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费）均由用户自行负担。</span><span style=\"color:#ED7D31;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#ED7D31;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">五、区块链服务使用准则</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.1. </span><span style=\"font-family:宋体;\">用户可在法律允许的范围内使用火花区块链接入平台提供的区块链服务。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.2. </span><span style=\"font-family:宋体;\">协议期限内，火花区块链接入平台根据用户购买的服务标准向用户提供技术支持服务（用户可登录火花区块链接入平台查看购买）。因用户未及时更新联系方式或联系方式不正确而导致火花区块链接入平台未能及时进行反馈的，火花区块链接入平台不承担由此产生的责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.3. </span><span style=\"font-family:宋体;\">用户严禁进行如下操作。如因用户进行如下操作造成的所有损失，应由用户自行承担；若由此给火花区块链接入平台造成损害的，用户应承担全部赔偿责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.3.1.</span><span style=\"font-family:宋体;\">将火花区块链接入平台提供的部署程序，以及运行数据（包括日志，数据库等）泄露给任何第三方。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.3.2. </span><span style=\"font-family:宋体;\">将环境、用户名、密码，泄露给任何第三方，从而导致第三方登陆研究部署程序等情形，由此所引起的结果由用户自行承担全部责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.4. </span><span style=\"font-family:宋体;\">用户对自己存放在火花区块链接入平台及区块链上的数据以及进入和管理平台上各类产品与服务的口令、密码等等完整性和保密性负责。用户不得泄露用户账号、密码、根证书、服务器证书以及相应私钥并应妥善保管好所有私钥文件；用户不得泄露区块链密钥，操作端密钥等；若意外泄露，请立即登陆系统进行重置或更新，并通知火花区块链接入平台客服邮箱：（</span><span style=\"background:yellow;\">it@sparkchain.cn</span><span style=\"font-family:宋体;color:#4472C4;\">）</span><span style=\"font-family:宋体;\">。因用户自身维护不当或保密不当所引起的一切损失和后果均由用户自行承担。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.5. </span><span style=\"font-family:宋体;\">用户对自己存放在火花区块链接入平台上的数据内容负责。如因上传、发布的公开信息违反法律法规、部门规章或国家政策，由此造成的全部结果及责任由用户自行承担。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.6. </span><span style=\"font-family:宋体;\">用户了解火花区块链接入平台无法保证其所提供的服务毫无瑕疵（如火花区块链接入平台及其任何产品或服务并不能保证用户的硬件或软件的绝对安全），用户同意：即使火花区块链接入平台或其任何产品或服务存在瑕疵，但上述瑕疵是当时行业技术水平所无法避免的，其将不被视为火花区块链接入平台违约。用户同意和火花区块链接入平台一同合作解决上述瑕疵问题。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.7. </span><span style=\"font-family:宋体;\">数据备份系用户的义务和责任。<b>火花区块链接入平台不保证完全备份用户数据，亦不对用户数据备份工作或结果承担任何责任</b>。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8. </span><span style=\"font-family:宋体;\">用户在购买、使用区块链服务时须遵守相关法律法规，维护互联网秩序和安全，不得从事违法犯罪行为，也不得为任何违反法律法规的行为提供便利。禁止行为包括但不限于：</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.1. </span><span style=\"font-family:宋体;\">实施涉嫌博彩、赌博、“私服”、“外挂”等非法互联网活动，或发布违法违规信息；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.2. </span><span style=\"font-family:宋体;\">实施诈欺、虚伪不实或误导行为，或实施侵害他人知识产权等任何合法权益的行为；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.3. </span><span style=\"font-family:宋体;\">发布、传播不受欢迎的广告、垃圾邮件（</span><span>SPAM</span><span style=\"font-family:宋体;\">）或包含反动、色情等有害信息的电子邮件；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.4. </span><span style=\"font-family:宋体;\">以过度负荷或其他任何方式导致网络中断，或实施未经授权的截取、盗用、干扰或监测等违法行为；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.5. </span><span style=\"font-family:宋体;\">实施任何破坏或试图破坏网络安全的行为，包括但不限于以病毒、木马、恶意代码、钓鱼等方式，对网站、服务器进行恶意扫描、非法侵入系统、非法获取数据等；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.6. </span><span style=\"font-family:宋体;\">实施任何改变或试图改变区块链服务提供的系统配置或破坏系统安全的行为；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.7. </span><span style=\"font-family:宋体;\">利用技术或其他手段破坏、扰乱区块链服务的运营或影响他人对区块链服务的使用；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.8. </span><span style=\"font-family:宋体;\">其它可能给火花区块链接入平台造成不良影响或损失的行为；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.8.9. </span><span style=\"font-family:宋体;\">包含法律或行政法规禁止内容的其他内容。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.9. </span><span style=\"font-family:宋体;\">用户应采取一切合理手段确保主机安全包括但不限于下述</span><span>: </span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.9.1. </span><span style=\"font-family:宋体;\">用户需要配合火花区块链接入平台了解主机情况，进行评估。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.9.2. </span><span style=\"font-family:宋体;\">用户有责任保证主机的安全性，包括</span><span>:</span><span style=\"font-family:宋体;\">确保部署环境安全可信，并定期扫描安全问题等。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.9.3. </span><span style=\"font-family:宋体;color:red;\">用户应将节点部署在火花区块链接入平台所支持的公有云上（用户可登录火花区块链接入平台查看）。一旦用户作出选择，应视为用户的自行决定。用户应对所决定部署的公有云及自行接入的外部节点安全性及相应后果负责。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.10. </span><span style=\"font-family:宋体;\">用户应采取一切合理行为确保以下信息安全包括但不限于下述</span><span>:</span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.10.1. </span><span style=\"font-family:宋体;\">妥善保管操作端和区块链节点的私钥和凭证信息，包括但不限于账户信息等。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.10.2. </span><span style=\"font-family:宋体;\">做好身份凭证</span><span>(</span><span style=\"font-family:宋体;\">私钥</span><span>)</span><span style=\"font-family:宋体;\">丢失预案，如</span><span>:</span><span style=\"font-family:宋体;\">主机病毒感染或其他因素造成的丢失等。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>5.11. </span><span style=\"font-family:宋体;\">火花区块链接入平台有权对用户使用火花区块链接入平台及产品、服务的情况进行监督和独立判断，如经由内部审核、通知、举报等途径发现用户在使用火花区块链接入平台所提供的产品时违反任何法律法规或本协议的规定，火花区块链接入平台有权要求用户改正或直接采取一切火花区块链接入平台认为必要的措施（包括但不限于更改或删除开发者上载的内容、暂停或终止开发者使用网络服务的权利、终止向用户提供部分或全部产品服务等），以减轻用户不当行为给火花区块链接入平台造成的影响，由此给火花区块链接入平台及其他第三方造成的损失，由用户承担。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">六、支付及费用</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>6.1. </span><span style=\"font-family:宋体;\">用户可购买火花区块链接入平台提供的专业区块链服务，服务内容及价格以用户下单时确认为准。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>6.2. </span><span style=\"font-family:宋体;\">如用户有违反有关法律法规以及本协议的行为，包括但不限于发布有关法律法规或本协议中明令禁止发布的信息，或有证据表明用户有欺诈、滥用信息或其他火花区块链接入平台认为不恰当的行为，火花区块链接入平台将有权单方决定中断或终止向用户提供服务，并将不退还用户已支付费用，且不承担任何责任，由此给用户带来的损失由用户自行承担。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">七、免责声明</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>7.1. </span><span style=\"font-family:宋体;\">火花区块链接入平台对任何第三方通过火花区块链接入平台可能对用户造成的任何错误、中伤、诽谤、诬蔑、不作为、谬误、淫秽、色情或亵渎，我们不承担责任。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>7.2. </span><span style=\"font-family:宋体;\">对黑客行为、计算机或手机病毒、或因用户保管疏忽致使帐号、密码被他人非法使用、盗用、篡改的或丢失，或由于与本网站链接的其它网站所造成用户个人资料的泄露，或用户因其他非火花区块链接入平台原因造成的损失，我们不承担责任。如用户发现任何非法使用用户帐号或安全漏洞的情况，请立即与我们联系。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>7.3. </span><span style=\"font-family:宋体;\">因任何非火花区块链接入平台原因造成的服务中断或其他缺陷，我们不承担任何责任。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>7.4. </span><span style=\"font-family:宋体;\">火花区块链接入平台所提供的区块链服务包含第三方提供的底层区块链服务，我们无法控制任何第三方底层区块链引起的风险，我们不保证非火花区块链平台自主运营的底层区块链服务一定能满足用户的要求；不保证底层区块链服务不会中断，也不保证底层区块链服务的及时性、安全性、准确性。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>7.5. </span><span style=\"font-family:宋体;\">任何情况下，火花区块链接入平台不对用户在区块链之上的业务功能承担责任（包括但不限于用户终止其区块链应用后的善后工作等）。因使用火花区块链接入平台而引起或与使用火花区块链接入平台有关的而产生的由我们负担的责任总额，无论是基于合同、保证、侵权、产品责任、严格责任或其它理论，均不得超过用户购买当次区块链服务所实际支付的费用总额（若有）。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">八、区块链服务变更、中断或终止</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>8.1. </span><span style=\"font-family:宋体;\">如因升级的需要而需暂停区块链服务、或调整区块链服务内容，我们将尽可能在网站上进行通告。由于用户未能及时浏览通告而造成的损失，我们不承担任何责任。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>8.2. </span><span style=\"font-family:宋体;\">用户明确同意，我们保留根据实际情况随时调整火花区块链接入平台提供的区块链服务内容、种类和形式的权利。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>8.3. </span><span style=\"font-family:宋体;\">发生下列任何一种情形，我们有权单方面中断或终止向用户提供区块链服务而无需通知用户，且无需对用户或第三方承担任何责任：</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>8.3.1. </span><span style=\"font-family:宋体;\">用户提供的信息不真实；</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>8.3.2. </span><span style=\"font-family:宋体;\">用户违反区块链服务条款；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>8.3.3. </span><span style=\"font-family:宋体;\">用户可随时根据规则取消区块链服务。自用户成功取消区块链服务之日起，我们不再向用户承担任何形式的责任。</span><span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span><span>&nbsp;</span></span></b><b><span style=\"font-family:宋体;\">九、隐私及个人信息的保护</span><span></span></b> \n</p>\n<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n	<span style=\"font-family:宋体;\">您的信任对火花区块链接入平台非常重要，火花区块链接入平台深知用户信息安全的重要性，火花区块链接入平台将按照法律法规要求，采取安全保护措施，保护您的用户信息安全可控。具体详见《法律声明及隐私权政策》。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十、知识产权及其它权利</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>10.1. </span><span style=\"font-family:宋体;\">非因本协议而产生的知识产权，归属依法已经拥有该权利的一方所有。除非另有约定，任何一方均不可凭借本协议取得另一方所拥有的著作权、专利权、商标权或任何其他知识产权。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>10.2. </span><span style=\"font-family:宋体;\">用户不得上传任何侵犯火花区块链接入平台或任意第三方合法权利的作品，否则一切后果由用户自行承担。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>10.3. </span><span style=\"font-family:宋体;\">用户不得出租、出借、拷贝、仿冒、复制或修改火花区块链接入平台任何部分或用于其他任何商业目的，也不得将该软件做反向工程、反编译或反汇编，或以其他方式或工具取得火花区块链接入平台之目标程序或源代码。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>10.4. </span><span style=\"font-family:宋体;\">对于用户上传到火花区块链接入平台可公开获取区域的任何内容，用户同意火花区块链接入平台在全世界范围内具有免费的、永久性的、不可撤销的、非独家的和完全再许可的权利和许可，以使用、复制、修改、改编、出版、翻译、据以创作衍生作品、传播、表演和展示此等内容（整体或部分），或将此等内容编入当前已知的或以后开发的其他任何形式的作品、媒体或技术中。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">其他具体信息详见《法律声明及隐私权政策》的法律声明部分。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十一、特别约定</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.1. </span><span style=\"font-family:宋体;\">用户购买、使用区块链服务的行为若有任何违反国家法律法规或侵犯任何第三方的合法权益的情形时，我们有权直接删除该等违反规定之信息，并可以暂停或终止向用户提供区块链服务。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.2. </span><span style=\"font-family:宋体;\">若用户利用区块链服务从事任何违法或侵权行为，由用户自行承担全部责任，因此给我们或任何第三方造成任何损失，用户应负责全额赔偿，并使我们免受由此产生的任何损害。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.3. </span><span style=\"font-family:宋体;\">用户同意我们通过重要页面的公告、通告、电子邮件以及常规信件的形式向用户传送与区块链服务有关的任何通知和通告。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.4. </span><span style=\"font-family:宋体;\">如用户有任何有关与区块链服务的相关投诉，请用户与我们联系，我们将在接到投诉之日起</span><span>15</span><span style=\"font-family:宋体;\">日内进行答复。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.5. </span><span style=\"font-family:宋体;\">区块链服务协议之效力、解释、执行均适用中华人民共和国法律。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.6. </span><span style=\"font-family:宋体;\">若非火花区块链接入平台更新本协议，用户再确认同意、签署本协议后，其效力将及于用户此时及未来登陆火花区块链接入平台时所有操作。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.7. </span><span style=\"font-family:宋体;\">用户在本协议项下对我们的授权将视为对我们及我们之关联公司的授权。我们及我们关联公司均可凭借用户的授权及本协议约定执行相关操作。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.8. </span><span style=\"font-family:宋体;\">如就本协议内容或其执行发生任何争议，应尽量友好协商解决；协商不成时，任何一</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">方均可向上海市的人民法院提起诉讼。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>11.9. </span><span style=\"font-family:宋体;\">区块链服务协议中的标题仅为方便而设，不影响对于条款本身的解释。</span><span style=\"color:#C45911;\"></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十二、保密</span><span></span></b> \n</p>\n<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n	<span style=\"font-family:宋体;\">火花区块链接入平台承诺对您注册账户时或使用火花区块链接入平台服务时提交或知悉的信息采取保密措施，不向第三方披露您的信息，除非：</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>12.1. </span><span style=\"font-family:宋体;\">依据本服务条款或者您与火花区块链接入平台之间其他服务协议、合同、在线条款等规定可以提供的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>12.2. </span><span style=\"font-family:宋体;\">依据法律法规的规定或行政、司法等职权部门要求应当提供的；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>12.3. </span><span style=\"font-family:宋体;\">在不违反本服务条款约定责任的前提下，该保密信息已经公开或能从公开领域获得。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十三、信息安全事件</span><span></span></b> \n</p>\n<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n	<span style=\"font-family:宋体;\">火花区块链接入平台将及时响应信息安全事件，并依据国家法律法规的要求、遵照相关安全事件处理标准管理信息安全事件。当发生涉及您相关资产的信息安全事件时，我们会依法及时将事件相关的情况通过邮件、信函、电话、推送通知等方式告知，您也可通过客服电话、大客户经理电话等方式主动联系我们。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十四、责任范围和责任限制</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span>14.1. </span><span style=\"font-family:宋体;\">您了解并同意，您应承担因您使用本服务、违反本服务条款或在您的账户下采取的任何行动而导致的任何第三方索赔。如果由此引起火花区块链接入平台及其关联公司、员工、客户和合作伙伴被第三方索赔的，您应负责处理，并赔偿火花区块链接入平台及其关联公司由此遭受的全部损失和责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>14.2. </span><span style=\"font-family:宋体;\">在适用法律许可的范围内，火花区块链接入平台不对与本服务条款有关或由本服务条款引起的任何间接的、惩罚性的、特殊的、派生的损失承担赔偿责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>14.3. </span><span style=\"font-family:宋体;\">火花区块链接入平台在此提示，您在使用火花区块链接入平台服务期间应当遵守中华人民共和国的法律，不得危害网络安全，不得利用火花区块链接入平台的服务从事侵犯他人名誉、隐私、知识产权和其他合法权益的活动。火花区块链接入平台不对您使用火花区块链接入平台服务的违法或违约行为承担任何责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>14.4. </span><span style=\"font-family:宋体;\">火花区块链接入平台用户在火花区块链接入平台网站自行上传、提供、发布相关信息，包括但不限于用户名称、公司名称、</span> <span style=\"font-family:宋体;\">联系人及联络信息，相关图片、资讯等，均由用户自行提供，火花区块链接入平台的用户须对其提供的前述信息依法承担全部责任。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>14.5. </span><span style=\"font-family:宋体;\">在某些情况下，为了帮助您更便捷的使用火花区块链接入平台的服务，火花区块链接入平台可能会展示火花区块链接入平台的参考代码或软件（如属于第三方开源软件，您则应遵守第三方开源软件的相关使用要求），基于相应展示页面的使用说明，该等软件可能允许您予以下载、进行二次开发等相关操作，您应理解并承诺，该等代码的知识产权属于火花区块链接入平台，您在使用时应明确表明权利人，同时，火花区块链接入平台不对您能否使用以及使用该等代码、软件的工作和后果负责。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十五、不可抗力和意外事件</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span style=\"font-family:宋体;\">火花区块链接入平台的网站可能因为下列不可抗力或意外事件无法正常运行的，火花区块链接入平台不承担损害赔偿责任：</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>15.1. </span><span style=\"font-family:宋体;\">因自然灾害、罢工、暴乱、战争、政府行为、司法行政命令等不可抗力因素；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>15.2. </span><span style=\"font-family:宋体;\">因电力供应故障、通讯网络故障等公共服务因素；</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>15.3. </span><span style=\"font-family:宋体;\">经提前公告或通知的，火花区块链接入平台在短时间内的系统维护。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span><span>&nbsp;</span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">十六、通知送达</span><span></span></b> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>16.1. </span><span style=\"font-family:宋体;\">您理解并同意，火花区块链接入平台可通过网页公告、电子邮件、站内信、短信、电话、系统消息以及即时通信等以上一种或多种通知方式向您发送通知，且火花区块链接入平台可以信赖您所提供的联系信息是完整、准确且当前有效的；上述通知在发送成功后视为已送达。</span><span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span>16.2</span><span style=\"font-family:宋体;\">．除非本服务条款另有约定或火花区块链接入平台与您另行签订的协议明确规定了通知方式，您发送给火花区块链接入平台的通知，应当通过火花区块链接入平台对外正式公布的通信地址、传真号码、电子邮件地址等联系信息进行送达。</span> <span></span> \n</p>\n<p class=\"MsoNormal\">\n	<span></span> \n</p>\n<p class=\"MsoNormal\">\n	<b><span style=\"font-family:宋体;\">说明：本协议自发布之日起生效，</span><span>2020</span></b><b><span style=\"font-family:宋体;\">年</span><span>1</span></b><b><span style=\"font-family:宋体;\">月</span><span>1</span></b><b><span style=\"font-family:宋体;\">日前有效。</span><span></span></b> \n</p>\n<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n	<b><span></span></b> \n</p>\n<p>\n	<br />\n</p>','ddddd'),
(1087640660632141824,'邀请码过期时间','INVATE_CODE_EXPIRE','main','1065065711421882368','2019-01-22 17:18:36',NULL,'2019-01-22 17:18:36','86400',''),
(1087976538676133888,'APP创建默认查询次数','APP_DEFAULT_READ_NUM','APP','1065065711421882368','2019-01-23 15:33:15',NULL,'2019-01-23 15:33:15','0',''),
(1088335358535401472,'文件服务器上传地址','FILESERVER_UPLOAD_PATH','main','1065065711421882368','2019-01-24 15:19:04',NULL,'2019-01-24 15:19:04','http://upload.sparkchain.cn/fileserver/upload?appid=1087310643821805568','必须配置参数appid'),
(1088335567566929920,'文件服务器下载地址','FILESERVER_DOWN_PATH','main','1065065711421882368','2019-01-24 15:19:54',NULL,'2019-01-24 15:19:54','http://upload.sparkchain.cn/fileserver/down?appid=1087310643821805568','必须配置appid'),
(1089768142550335488,'免费截止时间','FREE_END_TIME','user','1069800137045311488','2019-01-28 14:12:27',NULL,'2019-01-28 14:12:27','2019-05-31','用户默认免费截止时间'),
(1095528142228946944,'控制台登录-RSA公钥','RSA_PUBLIC_KEY','user','1065065711421882368','2019-02-13 11:40:38','1065065711421882368','2019-07-03 14:00:19','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIBbp9AXj8QjITzZkbgU3mvKz/da9cM6zFfNcHCQShhGXeGhHlPeWH9qMyWVAFnfY/N42Htny1U/yadxmkJuOxv15o/7+l4w4UB29jAn7iqI0U0E8Mo6JbeSP9+ReH7pbnwtIUjElyfu4t/9e4CJIW3CB3jiAOsH8OeRdJ3a4lLwIDAQAB',''),
(1095528583658471424,'控制台登录-RSA私钥','RSA_PRIVATE_KEY','user','1065065711421882368','2019-02-13 11:42:23','1065065711421882368','2019-07-03 14:00:06','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIgFun0BePxCMhPNmRuBTea8rP91\nr1wzrMV81wcJBKGEZd4aEeU95Yf2ozJZUAWd9j83jYe2fLVT/Jp3GaQm47G/Xmj/v6XjDhQHb2MC\nfuKojRTQTwyjolt5I/35F4fulufC0hSMSXJ+7i3/17gIkhbcIHeOIA6wfw55F0ndriUvAgMBAAEC\ngYAGE7txOk+ddZenKthcUjqYxHU2NJvREr31VM6GkfCTBsdGVQPXqhxHJs3DwhnBJn2J9YFtPsnz\nj/0JKakLnHEvvxOKHHkr7T7E+L+JVHndRg1Jpy7NsgLFQHZj/0e2ihSm6blphewQPiwDahVVKnQe\nceKU9V3j9Dy/OacdkpTkAQJBAMmuHC3EchQX6VXhp8Lz/kqn+j0qJUwwPYM7M/KACxiFpITcTwTs\ncuDLY/qez4TYH8sm7rCHQEo0uQ0kJGR53k8CQQCsqICUJG9mgd0Yrbl/nOzEfZxIQ0DM6cm+VYOV\np8QGDPqkDUTjM+QkwiA4N5MzLUvf7FibmWPlh5U2PKgfdXMhAkBl7+f0Rhe3CkL5Ep59zl+YkoDM\nC5JgiP1+aB0glBGHqnz2XnheL7j84DC/iyeuqKKhRyNb/HDFURK7jqoKUwLHAkEAnWFy4pZbnRqe\nyJICVlpaT7kXrZ7wAFjSdlZIfS/RNJq8FLGrq1ZvjToOWHAAE1Qkp1YuZpbpQh3WWvvrW9GZQQJA\naH+vI5eMtvr+Y5k+00hRdtiwXTNOqfJha41cVgndhlLmv/BBPrG52ixhBFZdDLNUOnnb6WlzZwcm\nQ5AjOM8MxA==','pkcs8格式');

/*Table structure for table `sys_dict` */

CREATE TABLE `sys_dict` (
  `code` varchar(255) NOT NULL COMMENT '字典编码',
  `parent` varchar(255) NOT NULL COMMENT '父级',
  `name` varchar(60) NOT NULL COMMENT '字典名称',
  `vals` varchar(60) DEFAULT NULL COMMENT '对应值',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `sortnum` int(11) DEFAULT NULL COMMENT '排序号',
  `status` tinyint(6) NOT NULL COMMENT '状态',
  `creator` bigint(80) DEFAULT NULL COMMENT '创建人',
  `updater` bigint(80) DEFAULT NULL COMMENT '更新人',
  `levecode` varchar(255) DEFAULT NULL COMMENT '层级编码',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

/*Data for the table `sys_dict` */

/*Table structure for table `sys_operate_log` */

CREATE TABLE `sys_operate_log` (
  `id` bigint(80) NOT NULL COMMENT 'id',
  `module` int(200) DEFAULT NULL COMMENT '模块',
  `refval` varchar(500) DEFAULT NULL COMMENT '关联属性值',
  `type` int(5) DEFAULT NULL COMMENT '操作类型',
  `val` varchar(500) DEFAULT NULL COMMENT '值',
  `lever` int(11) DEFAULT NULL COMMENT '日志级别',
  `preval` varchar(500) DEFAULT NULL COMMENT '原值',
  `remark` varchar(1000) DEFAULT NULL COMMENT '说明',
  `creator` varchar(80) DEFAULT NULL COMMENT '创建人',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `refval` (`refval`) USING BTREE,
  KEY `module` (`module`) USING BTREE,
  KEY `creator` (`creator`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_operate_log` */

/*Table structure for table `sys_platform_notify` */

CREATE TABLE `sys_platform_notify` (
  `id` bigint(80) NOT NULL,
  `title` varchar(200) DEFAULT NULL COMMENT '通知标题',
  `content` text COMMENT '通知内容',
  `msg_type` int(11) NOT NULL COMMENT '类型,0:全平台通知,1:指定用户通知,2:指定应用通知',
  `user_id` bigint(80) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `appid` bigint(80) NOT NULL DEFAULT '0' COMMENT '应用ID',
  `label` varchar(100) DEFAULT NULL COMMENT '消息标签,分类',
  `push_type` int(11) NOT NULL COMMENT '推送类型,0:平台内部消息,1:短信通知',
  `popup` int(11) NOT NULL DEFAULT '0' COMMENT '弹出显示',
  `state` int(11) NOT NULL COMMENT '状态,1:正常,2:禁用,-1:删除',
  `creator` bigint(80) DEFAULT NULL COMMENT '创建者',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `appid` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知,站内消息';

/*Data for the table `sys_platform_notify` */

/*Table structure for table `sys_region` */

CREATE TABLE `sys_region` (
  `id` bigint(80) NOT NULL COMMENT 'ID',
  `parentid` bigint(80) DEFAULT NULL COMMENT '上级区划',
  `code` varchar(200) DEFAULT NULL COMMENT '编码',
  `name` varchar(500) DEFAULT NULL COMMENT '名称',
  `shortname` varchar(200) DEFAULT NULL COMMENT '简称',
  `allname` varchar(200) DEFAULT NULL COMMENT '全称',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `sortnum` int(11) DEFAULT NULL COMMENT '排序号',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划';

/*Data for the table `sys_region` */

/*Table structure for table `sys_resources` */

CREATE TABLE `sys_resources` (
  `id` bigint(80) NOT NULL COMMENT 'ID',
  `name` varchar(500) DEFAULT NULL COMMENT '资源名称',
  `alias_name` varchar(500) DEFAULT NULL COMMENT '资源别名',
  `res_type_code` varchar(50) DEFAULT NULL,
  `permissioncode` varchar(500) DEFAULT NULL,
  `systemid` varchar(50) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL COMMENT '状态（1:正常  2:禁用   -1：已删除）',
  `icon` varchar(500) DEFAULT NULL COMMENT '图标路径',
  `path` varchar(500) DEFAULT NULL COMMENT '资源路径',
  `description` varchar(1500) DEFAULT NULL COMMENT '描述',
  `levelcode` varchar(500) DEFAULT NULL,
  `parentid` varchar(50) DEFAULT NULL COMMENT '父资源ID',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `updater` bigint(80) DEFAULT NULL COMMENT '修改人',
  `creator` bigint(80) DEFAULT NULL COMMENT '创建人',
  `sortnum` int(11) DEFAULT NULL COMMENT '排序号',
  `res_addr_type` varchar(500) DEFAULT NULL COMMENT '资源所在分类（DICT)',
  `code` varchar(500) DEFAULT NULL COMMENT '资源编码',
  `resourceGroup` int(11) DEFAULT NULL COMMENT '资源分组1：控制台 2：APP管理端',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

/*Data for the table `sys_resources` */

insert  into `sys_resources`(`id`,`name`,`alias_name`,`res_type_code`,`permissioncode`,`systemid`,`state`,`icon`,`path`,`description`,`levelcode`,`parentid`,`createtime`,`updatetime`,`updater`,`creator`,`sortnum`,`res_addr_type`,`code`,`resourceGroup`) values 
(1062573564156182528,'资源管理','资源管理','2','read','user',1,'','/resourceIndex','系统资源',NULL,'1081022339941924864','2018-11-14 13:00:48','2019-05-16 09:50:13',1065065711421882368,NULL,1,'service','RESLIST',1),
(1067312024796528640,'用户管理','用户管理','2',NULL,NULL,1,'','/userIndex','用户列表',NULL,'1081022339941924864','2018-11-27 14:49:38','2019-05-16 10:48:15',1065065711421882368,1065065711421882368,5,'service','USER_LIST1',1),
(1067320243585548288,'角色管理','角色管理','2',NULL,NULL,1,'','/roleIndex','角色列表',NULL,'1081022339941924864','2018-11-27 15:28:17','2019-05-16 10:28:07',1065065711421882368,1065065711421882368,3,'service','roleList',1),
(1081022339941924864,'系统管理','系统管理','1',NULL,NULL,1,'','','',NULL,NULL,'2019-01-04 10:59:45','2019-09-16 17:02:47',1065065711421882368,1065065711421882368,990,'service','SYSTEMMANAGER',1),
(1095944279827677184,'资源查询','资源查询','3',NULL,NULL,1,'','/v1/resources/resourcesList','资源列表查询接口',NULL,'1081022339941924864','2019-02-14 15:14:13','2019-02-14 15:14:13',NULL,1069800137045311488,36,'service','RESOURCES_QUERY',1),
(1095949119039471616,'添加资源接口','','3',NULL,NULL,1,'','/v1/resources/saveResource','',NULL,'1062573564156182528','2019-02-14 15:33:26','2019-02-14 15:33:26',NULL,1069800137045311488,37,'service','RESOURCES_ADD_API',1),
(1100285715469565952,'资源列表页面左侧树形','','3',NULL,NULL,1,'','/v1/resources/treeList','资源列表页面左侧树形',NULL,'1081022339941924864','2019-02-26 14:45:32','2019-02-26 14:45:32',NULL,1069800137045311488,68,'service','API_RESORCE_TREE_LIST',1),
(1100286033141956608,'添加资源父级节点下拉树形','','3',NULL,NULL,1,'','/v1/resources/selectTree','添加资源父级节点下拉树形',NULL,'1081022339941924864','2019-02-26 14:46:47','2019-02-26 14:46:47',NULL,1069800137045311488,69,'service','API_RESOURCE_SELECT_TREE',1),
(1100286203749466112,'删除资源','','3',NULL,NULL,1,'','/v1/resources/remove','',NULL,'1081022339941924864','2019-02-26 14:47:28','2019-02-26 14:47:28',NULL,1069800137045311488,70,'service','API_RESOURCE_DELETE',1),
(1100286442912874496,'资源详情','','3',NULL,NULL,1,'','/v1/resources/getInfo','资源详情',NULL,'1081022339941924864','2019-02-26 14:48:25','2019-02-26 14:48:25',NULL,1069800137045311488,71,'service','API_RESOURCE_DETAIL',1),
(1100286691270197248,'通过PID查询资源','','3',NULL,NULL,1,'','/v1/resources/getByPid','通过PID查询资源',NULL,'1081022339941924864','2019-02-26 14:49:24','2019-02-26 14:49:24',NULL,1069800137045311488,72,'service','API_RESOURCE_GETBYPID',1),
(1100286928831381504,'资源绑定角色列表','','3',NULL,NULL,1,'','/v1/resources/bindResourceList','资源绑定角色列表',NULL,'1081022339941924864','2019-02-26 14:50:21','2019-03-06 13:16:15',1070503158926540800,1069800137045311488,73,'service','API_RESOURCE_BIND_ROLE_LIST',1),
(1100287221258256384,'添加资源','','3',NULL,NULL,1,'','/v1/resources/saveResource','添加资源',NULL,'1081022339941924864','2019-02-26 14:51:31','2019-02-26 14:51:31',NULL,1069800137045311488,74,'service','API_RESOURCE_SAVE_RESOURCE',1),
(1100287379194773504,'编辑资源','','3',NULL,NULL,1,'','/v1/resources/updateResource','',NULL,'1081022339941924864','2019-02-26 14:52:08','2019-02-26 14:52:08',NULL,1069800137045311488,75,'service','API_RESOURCE_UPDATE',1),
(1100292181207285760,'角色详情','','3',NULL,NULL,1,'','/v1/role/getInfo','角色详情',NULL,'1081022339941924864','2019-02-26 15:11:13','2019-02-26 15:11:13',NULL,1069800137045311488,75,'service','API_ROLE_GETINFO',1),
(1100292395511054336,'删除角色','','3',NULL,NULL,1,'','/v1/role/remove','删除角色',NULL,'1081022339941924864','2019-02-26 15:12:04','2019-02-26 15:12:04',NULL,1069800137045311488,76,'service','API_ROLE_DELETE',1),
(1100292574519754752,'角色列表','','3',NULL,NULL,1,'','/v1/role/roleList','角色列表',NULL,'1081022339941924864','2019-02-26 15:12:47','2019-02-26 15:12:47',NULL,1069800137045311488,77,'service','API_ROLE_LIST',1),
(1100292915395035136,'新增角色','','3',NULL,NULL,1,'','/v1/role/saveRole','新增角色',NULL,'1081022339941924864','2019-02-26 15:14:08','2019-02-26 15:14:08',NULL,1069800137045311488,78,'service','API_ROLE_SAVE',1),
(1100293367507451904,'编辑角色','','3',NULL,NULL,1,'','/v1/role/updateRole','编辑角色',NULL,'1081022339941924864','2019-02-26 15:15:56','2019-02-26 15:15:56',NULL,1069800137045311488,79,'service','API_ROLE_UPDATE',1),
(1100293542590283776,'角色列表','','3',NULL,NULL,1,'','/v1/role/userRoleList','角色列表',NULL,'1081022339941924864','2019-02-26 15:16:38','2019-02-26 15:16:38',NULL,1069800137045311488,80,'service','API_ROLE_LIST',1),
(1100294526842437632,'用户角色列表','','3',NULL,NULL,1,'','/v1/role/userRoleList','用户角色列表',NULL,'1081022339941924864','2019-02-26 15:20:32','2019-02-26 15:20:32',NULL,1069800137045311488,81,'service','API_USER_ROLE',1),
(1100294722326364160,'角色绑定资源','','3',NULL,NULL,1,'','/v1/roleresources/bindResource','角色绑定资源',NULL,'1081022339941924864','2019-02-26 15:21:19','2019-02-26 15:21:19',NULL,1069800137045311488,82,'service','API_ROLE_BIND_RESOURCE',1),
(1100295178133962752,'用户绑定角色','','3',NULL,NULL,1,'','/v1/userrole/bindUser','用户绑定角色',NULL,'1081022339941924864','2019-02-26 15:23:08','2019-02-26 15:23:08',NULL,1069800137045311488,83,'service','API_USER_BIND_ROLE',1),
(1100295393452752896,'用户解除绑定角色','','3',NULL,NULL,1,'','/v1/userrole/unBindUser','用户解除绑定角色',NULL,'1081022339941924864','2019-02-26 15:23:59','2019-02-26 15:23:59',NULL,1069800137045311488,84,'service','API_USER_UNBIND_ROLE',1),
(1100295728829300736,'已绑定某角色用户列表','','3',NULL,NULL,1,'','/v1/userextend/bindUserList','角色通过参数传递',NULL,'1081022339941924864','2019-02-26 15:25:19','2019-02-26 15:27:44',1069800137045311488,1069800137045311488,85,'service','API_BIND_USER_LIST',1),
(1100297587006636032,'系统查看用户详情','','3',NULL,NULL,1,'','/v1/userextend/getInfo','系统用户查看用户详情',NULL,'1081022339941924864','2019-02-26 15:32:42','2019-02-26 15:32:42',NULL,1069800137045311488,88,'service','API_USER_INFO',1),
(1100300403569852416,'获取用户角色资源信息','','3',NULL,NULL,1,'','/v1/userextend/getRoleResourceByUser','获取用户角色资源信息',NULL,'1081022339941924864','2019-02-26 15:43:53','2019-02-26 15:43:53',NULL,1069800137045311488,89,'service','API_GETROLERESOURCE_BY_USER',1),
(1100300755417432064,'添加用户','','3',NULL,NULL,1,'','/v1/userextend/userAdd','个人用户',NULL,'1081022339941924864','2019-02-26 15:45:17','2019-02-26 15:45:17',NULL,1069800137045311488,90,'service','API_ADD_USER',1),
(1100301049287147520,'已绑定某角色用户','','3',NULL,NULL,1,'','/v1/userextend/userBindList','参数rid',NULL,'1081022339941924864','2019-02-26 15:46:27','2019-02-26 15:46:27',NULL,1069800137045311488,91,'service','API_USER_BIND_LIST',1),
(1100301289704652800,'用户列表','','3',NULL,NULL,1,'','/v1/userextend/userList','',NULL,'1081022339941924864','2019-02-26 15:47:25','2019-02-26 15:47:25',NULL,1069800137045311488,92,'service','API_USER_LIST',1),
(1100301437377708032,'编辑个人用户','','3',NULL,NULL,1,'','/v1/userextend/userUpdate','',NULL,'1081022339941924864','2019-02-26 15:48:00','2019-02-26 15:48:00',NULL,1069800137045311488,93,'service','API_USER_UPDATE',1),
(1103164694207660033,'登录用户信息接口','','3',NULL,NULL,1,'','/v1/userextend/userInfo','',NULL,'1081022339941924864','2019-03-06 13:25:34','2019-03-06 13:32:52',1070881006015217664,1070881006015217664,163,'service','API_LOGIN_USER_INFO',1),
(1118817426121691136,'系统配置','系统配置','1',NULL,NULL,1,'','','',NULL,NULL,'2019-04-18 18:03:56','2019-09-16 17:02:00',1065065711421882368,1065065711421882368,980,'service','CHAIN_MANAGER_FOLDER',1),
(1128872249491722240,'配置列表','配置列表','2',NULL,NULL,1,'','/common/configIndex','系统配置',NULL,'1118817426121691136','2019-05-16 11:58:12','2019-07-30 09:48:11',1065065711421882368,1065065711421882368,259,'service','plaf-attr-config',1),
(1128872249491722241,'配置添加','配置添加','3',NULL,NULL,1,'','/common/configAdd','配置添加',NULL,'1118817426121691136','2019-05-16 11:58:12','2019-09-16 13:46:28',1065065711421882368,1065065711421882368,260,'service','plaf-attr-config-add',1),
(1173517398578302976,'系统通知','系统通知','1',NULL,NULL,1,'','','',NULL,NULL,'2019-09-16 16:42:05','2019-09-16 16:42:05',NULL,1065065711421882368,991,'service','YSY_NOTIFY',1),
(1173517630544285696,'通知列表','通知列表','2',NULL,NULL,1,'','/platformnotify/list','',NULL,'1173517398578302976','2019-09-16 16:43:01','2019-09-16 16:44:06',1065065711421882368,1065065711421882368,992,'service','YSY_NOTIFY_LIST',1);

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `id` bigint(80) NOT NULL,
  `name` varchar(500) NOT NULL COMMENT '名称',
  `state` smallint(6) NOT NULL COMMENT '状态（1:正常  2:禁用   -1：已删除）',
  `description` varchar(500) DEFAULT NULL COMMENT '说明',
  `creator` bigint(80) DEFAULT NULL COMMENT '创建人',
  `updater` bigint(80) DEFAULT NULL COMMENT '修改人',
  `sortNum` int(11) DEFAULT NULL COMMENT '排序号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`state`,`description`,`creator`,`updater`,`sortNum`,`createTime`,`updateTime`,`code`) values 
(1063043360945602560,'admin',1,'超级管理员',NULL,1065065711421882368,1,'2018-11-15 20:17:42','2018-11-28 17:36:31','admin');

/*Table structure for table `sys_role_resources` */

CREATE TABLE `sys_role_resources` (
  `id` bigint(80) NOT NULL,
  `rid` bigint(80) DEFAULT NULL COMMENT '角色id',
  `res_id` bigint(80) DEFAULT NULL COMMENT '资源id',
  `updater` bigint(80) DEFAULT NULL COMMENT '修改人',
  `creator` bigint(80) DEFAULT NULL COMMENT '创建人',
  `sortNum` int(11) DEFAULT NULL COMMENT '排序号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `state` smallint(6) DEFAULT NULL COMMENT '状态（1:正常  2:禁用   -1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源表';

/*Data for the table `sys_role_resources` */

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `id` bigint(80) NOT NULL COMMENT 'ID',
  `mobile` varchar(50) NOT NULL COMMENT '登录号码',
  `loginemail` varchar(50) DEFAULT NULL COMMENT '登录邮箱',
  `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(60) DEFAULT NULL,
  `pingyinname` varchar(150) DEFAULT NULL COMMENT '姓名拼音',
  `nickname` varchar(60) DEFAULT NULL,
  `icon` varchar(150) DEFAULT NULL COMMENT '头像(图片地址)',
  `salt` varchar(60) DEFAULT NULL COMMENT '加密盐',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `state` smallint(6) NOT NULL COMMENT '用户状态（1:正常  2:禁用   -1：已删除）',
  `explain` varchar(2000) DEFAULT NULL COMMENT '异常说明',
  `organization` tinyint(4) DEFAULT NULL COMMENT '组织类型（0:个人  1：企业）',
  `verifytime` datetime DEFAULT NULL COMMENT '审核时间',
  `verifyuser` bigint(80) DEFAULT NULL COMMENT '审核人',
  `rank` tinyint(4) DEFAULT '0' COMMENT '等级',
  `vip` tinyint(4) DEFAULT '0' COMMENT '特权用户（0：普通用户 ，1：特权用户）',
  `vipEndtime` datetime DEFAULT NULL COMMENT '特权结束时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`mobile`,`loginemail`,`password`,`name`,`pingyinname`,`nickname`,`icon`,`salt`,`createtime`,`state`,`explain`,`organization`,`verifytime`,`verifyuser`,`rank`,`vip`,`vipEndtime`) values 
(1065065711421882368,'13810001001',NULL,'e10adc3949ba59abbe56e057f20f883e','系统管理员',NULL,'admin',NULL,'123456','2018-11-21 10:00:44',1,NULL,0,NULL,NULL,0,1,'2019-05-31 00:00:00')
/*Table structure for table `sys_user_extends` */

CREATE TABLE `sys_user_extends` (
  `uid` bigint(80) NOT NULL COMMENT '用户id',
  `sex` varchar(50) DEFAULT NULL COMMENT '性别（men,women）',
  `region` bigint(80) DEFAULT NULL COMMENT '所在区域',
  `cardnum` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `office_mobile` varchar(50) DEFAULT NULL COMMENT '办公手机号码',
  `office_tel` varchar(50) DEFAULT NULL COMMENT '办公固定电话',
  `office_email` varchar(50) DEFAULT NULL COMMENT '办公邮箱',
  `office_addr` varchar(500) DEFAULT NULL COMMENT '办公地址',
  `birthday` datetime DEFAULT NULL COMMENT '生日日期',
  `descriptions` text COMMENT '说明',
  `registtime` datetime DEFAULT NULL COMMENT '注册时间',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ号码',
  `weixin` varchar(250) DEFAULT NULL COMMENT '微信号码',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `companyName` varchar(200) DEFAULT NULL COMMENT '用户公司名称',
  `cardnumFrontFile` varchar(300) DEFAULT NULL COMMENT '身份证正面文件',
  `cardnumBackFile` varchar(300) DEFAULT NULL COMMENT '身份证反面文件',
  `cardnumHandFile` varchar(300) DEFAULT NULL COMMENT '身份证手持文件',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展信息表';

/*Data for the table `sys_user_extends` */

insert  into `sys_user_extends`(`uid`,`sex`,`region`,`cardnum`,`office_mobile`,`office_tel`,`office_email`,`office_addr`,`birthday`,`descriptions`,`registtime`,`qq`,`weixin`,`createtime`,`updatetime`,`companyName`,`cardnumFrontFile`,`cardnumBackFile`,`cardnumHandFile`) values 
(1065065711421882368,'men',NULL,'1234567890123456798','','','','',NULL,NULL,'2018-11-30 13:45:19','','',NULL,NULL,'','','',''),
(1173494755099676672,NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,'2019-09-16 15:12:07',NULL,NULL,'2019-09-16 15:12:07',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `id` bigint(80) NOT NULL,
  `rid` bigint(80) DEFAULT NULL COMMENT '角色id',
  `uid` bigint(80) DEFAULT NULL COMMENT '用户id',
  `state` smallint(6) DEFAULT NULL COMMENT '状态（1:正常  2:禁用   -1：已删除）',
  `sortnum` int(11) DEFAULT NULL COMMENT '排序号',
  `updater` bigint(80) DEFAULT NULL COMMENT '修改人',
  `creator` bigint(80) DEFAULT NULL COMMENT '创建人',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`rid`,`uid`,`state`,`sortnum`,`updater`,`creator`,`createtime`,`updateTime`) values 
(1,1063043360945602560,1065065711421882368,1,0,0,0,'2019-09-16 13:35:28','2019-09-16 13:35:31');

/*Table structure for table `sys_user_token` */

CREATE TABLE `sys_user_token` (
  `id` bigint(80) NOT NULL,
  `uid` bigint(80) DEFAULT NULL COMMENT '用户id',
  `token` varchar(255) DEFAULT NULL COMMENT 'token',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `scope` int(11) DEFAULT NULL COMMENT '作用域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户token';

/*Data for the table `sys_user_token` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
