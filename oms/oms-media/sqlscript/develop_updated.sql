-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: oms_develop_9oct
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `act_evt_log`
--

DROP TABLE IF EXISTS `act_evt_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_evt_log`
--

LOCK TABLES `act_evt_log` WRITE;
/*!40000 ALTER TABLE `act_evt_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_evt_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ge_bytearray`
--

DROP TABLE IF EXISTS `act_ge_bytearray`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ge_bytearray`
--

LOCK TABLES `act_ge_bytearray` WRITE;
/*!40000 ALTER TABLE `act_ge_bytearray` DISABLE KEYS */;
INSERT INTO `act_ge_bytearray` VALUES ('1447502',1,'D:\\Projects\\Hotstar\\hotstar\\oms\\oms-media\\target\\classes\\processes\\oms_proposal_review.bpmn','1447501','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/test\">\r\n  <process id=\"oms_proposal_review\" name=\"My process\" isExecutable=\"true\">\r\n    <startEvent id=\"omsproposalstartevent\" name=\"Start\"></startEvent>\r\n    <userTask id=\"mpreviewtask\" name=\"Media Planner Review\" activiti:assignee=\"${proposalServiceImpl.getProposalAssignee(execution)}\"></userTask>\r\n    <exclusiveGateway id=\"exclusivegateway1\" name=\"Exclusive Gateway\"></exclusiveGateway>\r\n    <sequenceFlow id=\"flow4\" sourceRef=\"mpreviewtask\" targetRef=\"exclusivegateway1\"></sequenceFlow>\r\n    <userTask id=\"reviewproposaltask\" name=\"Pricing Review\" activiti:candidateUsers=\"${proposalServiceImpl.getProposalPriceAdmins(execution)}\"></userTask>\r\n    <serviceTask id=\"reviewservicetask\" name=\"Review Service\" activiti:expression=\"${proposalServiceImpl.proposalStatusUpdate(execution)}\"></serviceTask>\r\n    <sequenceFlow id=\"flow5\" sourceRef=\"exclusivegateway1\" targetRef=\"proposalPriceReviewTask\">\r\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${action == true}]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow id=\"flow6\" sourceRef=\"reviewproposaltask\" targetRef=\"reviewservicetask\"></sequenceFlow>\r\n    <sequenceFlow id=\"flow7\" sourceRef=\"reviewservicetask\" targetRef=\"mpreviewtask\"></sequenceFlow>\r\n    <exclusiveGateway id=\"exclusivegateway2\" name=\"Exclusive Gateway\"></exclusiveGateway>\r\n    <serviceTask id=\"proposalPriceReviewTask\" name=\"Proposal Price Review Service\" activiti:expression=\"${proposalServiceImpl.proposalStatusUpdate(execution)}\"></serviceTask>\r\n    <sequenceFlow id=\"flow14\" sourceRef=\"proposalPriceReviewTask\" targetRef=\"reviewproposaltask\"></sequenceFlow>\r\n    <serviceTask id=\"rejectedByAdminTask\" name=\"Rejected\" activiti:expression=\"${proposalServiceImpl.proposalStatusUpdate(execution)}\"></serviceTask>\r\n    <sequenceFlow id=\"flow16\" sourceRef=\"exclusivegateway2\" targetRef=\"rejectedByAdminTask\">\r\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${action == false}]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow id=\"flow19\" sourceRef=\"rejectedByAdminTask\" targetRef=\"mpreviewtask\"></sequenceFlow>\r\n    <serviceTask id=\"proposalApprovedTask\" name=\"Approved\" activiti:expression=\"${proposalServiceImpl.proposalApproved(execution)}\"></serviceTask>\r\n    <sequenceFlow id=\"flow20\" sourceRef=\"exclusivegateway2\" targetRef=\"proposalApprovedTask\">\r\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${action == true}]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <endEvent id=\"omsProposalEndEvent\" name=\"ProcessEnd\" activiti:async=\"true\" activiti:exclusive=\"false\"></endEvent>\r\n    <sequenceFlow id=\"flow21\" sourceRef=\"proposalApprovedTask\" targetRef=\"omsProposalEndEvent\"></sequenceFlow>\r\n    <serviceTask id=\"adminReviewTask\" name=\"Admin review Service\" activiti:expression=\"${proposalServiceImpl.proposalStatusUpdate(execution)}\"></serviceTask>\r\n    <sequenceFlow id=\"flow24\" sourceRef=\"exclusivegateway1\" targetRef=\"adminReviewTask\">\r\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${action == false}]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <userTask id=\"adminReview\" name=\"Admin Review\" activiti:candidateUsers=\"${proposalServiceImpl.getProposalAdmins()}\"></userTask>\r\n    <sequenceFlow id=\"flow25\" sourceRef=\"adminReviewTask\" targetRef=\"adminReview\"></sequenceFlow>\r\n    <sequenceFlow id=\"flow26\" sourceRef=\"adminReview\" targetRef=\"exclusivegateway2\"></sequenceFlow>\r\n    <serviceTask id=\"servicetask1\" name=\"In Progress\" activiti:expression=\"${proposalServiceImpl.proposalStatusUpdate(execution)}\"></serviceTask>\r\n    <sequenceFlow id=\"flow27\" sourceRef=\"omsproposalstartevent\" targetRef=\"servicetask1\"></sequenceFlow>\r\n    <sequenceFlow id=\"flow28\" sourceRef=\"servicetask1\" targetRef=\"mpreviewtask\"></sequenceFlow>\r\n  </process>\r\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_oms_proposal_review\">\r\n    <bpmndi:BPMNPlane bpmnElement=\"oms_proposal_review\" id=\"BPMNPlane_oms_proposal_review\">\r\n      <bpmndi:BPMNShape bpmnElement=\"omsproposalstartevent\" id=\"BPMNShape_omsproposalstartevent\">\r\n        <omgdc:Bounds height=\"35.0\" width=\"35.0\" x=\"370.0\" y=\"240.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"mpreviewtask\" id=\"BPMNShape_mpreviewtask\">\r\n        <omgdc:Bounds height=\"55.0\" width=\"105.0\" x=\"720.0\" y=\"230.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"exclusivegateway1\" id=\"BPMNShape_exclusivegateway1\">\r\n        <omgdc:Bounds height=\"40.0\" width=\"40.0\" x=\"864.0\" y=\"237.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"reviewproposaltask\" id=\"BPMNShape_reviewproposaltask\">\r\n        <omgdc:Bounds height=\"59.0\" width=\"112.0\" x=\"951.0\" y=\"70.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"reviewservicetask\" id=\"BPMNShape_reviewservicetask\">\r\n        <omgdc:Bounds height=\"69.0\" width=\"109.0\" x=\"796.0\" y=\"65.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"exclusivegateway2\" id=\"BPMNShape_exclusivegateway2\">\r\n        <omgdc:Bounds height=\"40.0\" width=\"40.0\" x=\"1252.0\" y=\"281.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"proposalPriceReviewTask\" id=\"BPMNShape_proposalPriceReviewTask\">\r\n        <omgdc:Bounds height=\"71.0\" width=\"131.0\" x=\"941.0\" y=\"146.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"rejectedByAdminTask\" id=\"BPMNShape_rejectedByAdminTask\">\r\n        <omgdc:Bounds height=\"55.0\" width=\"105.0\" x=\"720.0\" y=\"354.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"proposalApprovedTask\" id=\"BPMNShape_proposalApprovedTask\">\r\n        <omgdc:Bounds height=\"55.0\" width=\"106.0\" x=\"1330.0\" y=\"274.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"omsProposalEndEvent\" id=\"BPMNShape_omsProposalEndEvent\">\r\n        <omgdc:Bounds height=\"35.0\" width=\"35.0\" x=\"1470.0\" y=\"284.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"adminReviewTask\" id=\"BPMNShape_adminReviewTask\">\r\n        <omgdc:Bounds height=\"63.0\" width=\"120.0\" x=\"951.0\" y=\"274.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"adminReview\" id=\"BPMNShape_adminReview\">\r\n        <omgdc:Bounds height=\"55.0\" width=\"105.0\" x=\"1120.0\" y=\"278.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNShape bpmnElement=\"servicetask1\" id=\"BPMNShape_servicetask1\">\r\n        <omgdc:Bounds height=\"55.0\" width=\"105.0\" x=\"520.0\" y=\"230.0\"></omgdc:Bounds>\r\n      </bpmndi:BPMNShape>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow4\" id=\"BPMNEdge_flow4\">\r\n        <omgdi:waypoint x=\"825.0\" y=\"257.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"864.0\" y=\"257.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow5\" id=\"BPMNEdge_flow5\">\r\n        <omgdi:waypoint x=\"884.0\" y=\"237.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"883.0\" y=\"185.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"941.0\" y=\"181.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow6\" id=\"BPMNEdge_flow6\">\r\n        <omgdi:waypoint x=\"951.0\" y=\"99.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"905.0\" y=\"99.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow7\" id=\"BPMNEdge_flow7\">\r\n        <omgdi:waypoint x=\"796.0\" y=\"99.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"772.0\" y=\"101.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"772.0\" y=\"230.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow14\" id=\"BPMNEdge_flow14\">\r\n        <omgdi:waypoint x=\"1006.0\" y=\"146.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"1007.0\" y=\"129.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow16\" id=\"BPMNEdge_flow16\">\r\n        <omgdi:waypoint x=\"1272.0\" y=\"321.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"1270.0\" y=\"381.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"825.0\" y=\"381.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow19\" id=\"BPMNEdge_flow19\">\r\n        <omgdi:waypoint x=\"772.0\" y=\"354.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"772.0\" y=\"327.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"772.0\" y=\"285.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow20\" id=\"BPMNEdge_flow20\">\r\n        <omgdi:waypoint x=\"1292.0\" y=\"301.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"1330.0\" y=\"301.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow21\" id=\"BPMNEdge_flow21\">\r\n        <omgdi:waypoint x=\"1436.0\" y=\"301.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"1470.0\" y=\"301.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow24\" id=\"BPMNEdge_flow24\">\r\n        <omgdi:waypoint x=\"884.0\" y=\"277.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"884.0\" y=\"305.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"951.0\" y=\"305.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow25\" id=\"BPMNEdge_flow25\">\r\n        <omgdi:waypoint x=\"1071.0\" y=\"305.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"1120.0\" y=\"305.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow26\" id=\"BPMNEdge_flow26\">\r\n        <omgdi:waypoint x=\"1225.0\" y=\"305.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"1252.0\" y=\"301.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow27\" id=\"BPMNEdge_flow27\">\r\n        <omgdi:waypoint x=\"405.0\" y=\"257.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"520.0\" y=\"257.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n      <bpmndi:BPMNEdge bpmnElement=\"flow28\" id=\"BPMNEdge_flow28\">\r\n        <omgdi:waypoint x=\"625.0\" y=\"257.0\"></omgdi:waypoint>\r\n        <omgdi:waypoint x=\"720.0\" y=\"257.0\"></omgdi:waypoint>\r\n      </bpmndi:BPMNEdge>\r\n    </bpmndi:BPMNPlane>\r\n  </bpmndi:BPMNDiagram>\r\n</definitions>',0),('1447503',1,'D:\\Projects\\Hotstar\\hotstar\\oms\\oms-media\\target\\classes\\processes\\oms_proposal_review.oms_proposal_review.png','1447501','�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\�\0\0�\0\0\0�!N\"\0\0J2IDATx\�\�\��\\�}/�v�\�,a1{1K(L\�-.E(���ŵV\�\�^`�/<\�\�#Y�8��(\���VA �xm�X�&v(�D\�f��Y2`B<l\"d!�f���\Z=�\��?;=n��{z^�>�?��_\�\�sN?\�����\��\'�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0&GE��\�[�W�\\�\�E�E]]]�	���;Z�t\�\�fh�\0\0\0\0\0��e˖E���ў={T\���@�\�\�n\�\�\����\0\0\0\0�!aD��>U������׵\\\0\0\0\0�	S\��\�U]]]{�\\\0\0\0\0�	��S\�GZ.\0\0\0\0@��&�\�ݺ#�ꃫ�\n˂sa�h���\0\0\0\05\�\�w\�\�myo\����\r��a���\�`�\����ta}].f\�\�\�\0\0\0\0@\r�\��\�\�[�.�a�\�\�\�W6EK^\�\�<�f8�\�a�\�/m�����\�S?��\�\�\�ks1c6\0\0\0\0j�\�o\�>]��\�\�p~����/G6�\'\�\�\�\�b\�.l\0\0\0\0\�Po\Z�\'V�4\�?���Ma��ۚ\�\�\0\0\0\0��\�P\�\�7�0���^���/��}ے\n\�a]i��/�4��cnk\�c[o�\��ߏ^\�%��\�9�\�\0\0\0\0dV�\0�w\�\��\�sԇp�r���|�p�p_��дѶVY\�\����9\�ˏ_�O�ua��\�\0\0\0@&UP�MsF\�W\�\�U\�W�)4m��UֆW�e���T_]\��kw\0\0\0\0�M\�z�\�T��\�z}�\�\�\�\�6\�_�\0\0\0�L2\r�\�t*\�Ze�麹fX�9�\�\0\0\0\0dR�\��5|�ٰ�@Sh:ֶ&�\�\�\0\0\0\0��\�P�Z\�Su��j��/mh\nM\�\�\�J�j�-5\���\��\�\�\0\0\0\0 �j���F7\�_\�pX\�\�/G�\���B\�Q���zs��k��a��\�\0\0\0@&\�\nPw\�\�y\�\�d��/�-~m\�9\�\��\�z��_ٔ���-]\�ڽ[�)4u[+�-=��5Oߴ�8�\��\�\�\0\0\0\0 �\��!�\�{�\��\r�-��a��~\�{;�B\�q���Z�\�����a�s�\�\0\0\0@f5\Z��\�ݺc8�\�L�鄷�ݻ�7|\��S\�\�\���BH�\0\0\0��\ZMX����\�\�\��\'���k\�Y��}�\�\0\0\0\02GX/4��{w\��\�3\�+O�e͠�Ta���Q�\�\0\0\0\0d��^h:�mm�\��F\�kw\0\0\0\0\���Bөlk����7\�^{\�\�\0\0\0\0 ��Bөlkc\r\�K�=hw\0\0\0\0�	\�z������\0\0\0\00\��BSmM	\�\0\0\0`�	P��ښ\�\0\0\0��\nM�5%�\0\0\0�)&@\�^hZ(>�\�\�hkJX\0\0\0\0)!@\�Nh\ZB��Ƶ7�H[S\�z\0\0\0\0H	j�C\�ʐ�Tښ\�\0\0\0@JP\��\���τ\�n*Cza�\�\0\0\0@\�P\��VI/�W\�z\0\0\0\0H)jj\��T��\'�\0\0\0�Tj\�\05~i\�u\�!�D\'�xb�hѢ1��ǚ���瞋\�:\�\�\�C�;\�\�\�3ό�/_>�\�1���Vh\�\�\�~N|�V�\�\���v\�穧���\�񜗉>wi\�\�z\0\0\0\0h\�d��c\rPse\�o��,�t\�IM9\"8��\���^�:z�\��\�\�;.3#��\�\�yq;Y\�Ja}X^�xq��f:?i\�\�z\0\0\0\0hP\Z\��\�\�0�8,D3g\�\�~\�%�D7n�x\��d�3f$��}\�\�y�\�\��x�\��}�\�\�W\\�,\�;\�I\��o}+�}\�WFGq\�~�5<F\�/��\�\�=W\�5�}�\�\�駟\��\�_�~}4mڴ\�x��vŊ#>\�D��C#헵JX_\�\�*\�W\���\�K�>�嫯�::��ãO|\�ѽ�ޛl[�vm�\���9\�=M}CX\0\0\0\0\rJCX_\Z�^\Z\�\�\�ٙ\�\�W�Z�,�{\�ѦM��\�\�?>\�\�NHnoذa�ǫu��\�\�/��9\�d9Lg��q\�\�\�c�=6��\��{�w\�yg�__g͚���/m��\\�\��0	��\�\���\�\'\�!n\�Lth:4\�~E��\�\�\ZY\�i�U=_��\�:/�*a���5k\�$\�GuT�-��Ҷ��\�7\�7{\�\�\0\0\0@��=�/ULK#�è\�\�\�a]\�v\�)�$�KA\�\�\'��_�Y\��7\�x\�>#��<�\�}n�FWV��>�!���\0�\�*\�޵k\��\�\��W��z\�\�\n�\�D\�Y\�A%\�ٲe5\�W#\�%W%�/\�����\��C\�F\�\�\�7��\0\0\0\0Ѐ4���={v�?��\������#\�ö0\�7W \��\�\�_\Z\�F\n��\��\����}���\�}ݥ�ʥ��\�s媄��y��\��j\�%��\�V�m\�M@X_�]6֧�o\�\0\0\0��4��!8q\rӅ�0���#\�\��.Mr\�Yg\�(�9�\�χzh�ǫw�0\�x�l\�rX\�ϕM�R�Z\��\�a\�s\�=��\�\�rx�#=W�J\�\�\�/M�F�\�ʦ@��\\\��\�\�k��\\�a})D\�\���k �o��!�\0\0\0��\��!��Å7����)g\�0\�H\�0�v\�~۷oO�\�\rM/.�Y�x�\��4�\�\�wߝܞ;w\����{�an\�h�\�\n\�\��.MKR\�rU\�\�F^��u뒹\�C\�\Z�6	_4�{	\�\'7��u^r\r��\��!\��\�K/\�\�±i\�\�z\0\0\0\0hP���jꪕ\����#�8\"�\�;�\�{\�ڵ��\�\�\�\0\0\0\0 \��JX\�Fȇp>�h#\�\����v\0\0\0\0\'�W\�z�\�\0\0\0�\�+a�\�\�\0\0\0\0`�	땰^iw\0\0\0\00ń�JX��;\0\0\0\0�b\�z%�W\�\0\0\0\0L1a�\�+\�\0\0\0\0���^	\�v\0\0\0\0SLX���J�\0\0\0�)&�W\�z�\�\0\0\0�\�+a�\�\�\0\0\0\0`�	땰^iw\0\0\0\00ń�JX��;\0\0\0\0�b\�z%�W\�\0\0\0\0L1a�\�+\�\0\0\0\0���^	\�v\0\0\0\0SLX���.Z�\��Ko\�\�\�^\�\�\0\0\0\0�,a�\�JX�t\�\�\���~\�0�\�\�\��øݽ\�\0\0\0��%�WY	\�y\��,Y�\�w\�\�\�<�gD}껻�\�\��\�\�\0\0\0\0�,a�\�\�\�\�\�\�\�į��0�J�=�R>�`�j�~��\n\�\�uA=\0\0\0\0-OX�\\\�3�ta\�ӦM;\�\�\0\0\0\0��\�\�E?]\��\�\���\�\�\�W�\�\�y�\0\0\0\0d\�d��.�\�B��O>�_�\�P=\�\0\0\0\0@MvX.�ɸ�\�K�\�K�\�\�/pd\0\0\0\0 c&;��z\�O�\�\0�υ\�A}it��\�\0\0\0 cDX��o\�7��T\�b�\"G\0\0\0\02DXM\�7��\�\�\nG\0\0\0\02DXM\�/k��7w=\0\0\0\0d����_.�ԗ\�w�\0\0\0\0 #����\0\0\0\0SL\0�*\0\0\0\00\����\0\0\0\0SL\0�*\0\0\0\00\����\0\0\0\0SL\0�*\0\0\0\00\����\0\0\0\0SL\0H���\�\�\�zk�ʕ+�hѢ����%+�\�V�������K�n�k�\�\0\0\0\0@f	\�iv!�_�lY\�\�\�\�ٳG�`\rD\�>�\��\�\�\�/\�\0\0\0\0d���fF\�\�\��������\��\0\0\0\02IXO�S\��U�����z\0\0\0\0�$��مy\�\�j(��~\0\0\0@6	\�iv�	\�{��ત²�[X\0\0\0\0� ��\�\�\�w\�\�myo\����\r��a���\�`�\���oa=\0\0\0\04\'a=ͮVX��y\�\�E7\�_=�ʦh\�k�ќG\���a9����\���>\�x\�g\��{a=\0\0\0\04!a=ͮVX�y�`t\�ë�\���\�\��m\���\�[X\0\0\0\0\�GXO��7\r\��z\Z\��\�z���\0\0\0\0����fW/�Sߔ\��kz9z\�ǿ�\��mK*,�u�\�O�\�#�\�\0\0\0@s\�\�쪅��[w$�-��>����u\�s؇���\n���\0\0\0\0\�T��4�ja}�in\�h�\��ºj�\n���\0\0\0\0\�T��4;a�\�\0\0\0�y\�z��ip��\0\0\0�\�\�\�\�\�_`��\�̆}\�\�z\0\0\0\0hJ\�z�]����\�=U���V��\�F���\0\0\0\0����fW+�߼}0�a�\�\��k~9\�\�6(�\�\0\0\0@�\�\�\�j���;wG�<�f2\�͂ގ��\��ay\�k�\�ӯlJB�����v\�\�-�\�\0\0\0@�\�\�\�\�M���w}�\�p\�RX�K뷾�SP/�\0\0\0�\�%��\�\�\�+�w\�\�>,���\0\0\0\0�\n\�z�\�h\�z%�\0\0\0�T\�\�\��JX\0\0\0@\�	\�iv\�z%�\0\0\0 ��4;a�\�\0\0\0�y\�z���^	\�\0\0\0\�<a=\�NX���\0\0\0\0d���f\'�W\�z\0\0\0\02OXO�\�+a=\0\0\0\0�\'��\�	땰\0\0\0�\�\�\�\��JX\0\0\0@\�	\�iv\�\��x�pz\�ѩ��\Z=�\�sM6�^�x�Z�3�\�\�C�N<�\�hѢE�;\n\�\0\0\0�a=S�X,~<��\�[/�ˋ/N�C����~*~��\�~\�7�\�N:\�\�z\0\0\0\08���LQ��Th{q��\�\�gwvv~�־#���\�aTx���\�>;:��ӓu[�l���\�\�\�KF�_y\�\��\�\��\�?~\r\����\�z��=�\�c\�)���<o\��o\�ڵɶg�}6:�䓓mG}t���᰾�ߧ|��\�\�GӦMK�!��+��\�̙3�\�^\�%�\\mܸqT\�]\�1x\��d�3f$��}\�\�y�\�\��x�^C8.\�؆\�\�|\�;\�}���o%�\�q>\�#��\0\0\0\0�a=S����cCa}�\�9���ܷё���v\�>\�\�T.�6mJօP8�!����\��\����(	\�\��g?�\�\�w\�G&�׭[�LY�Kaz\�ú\r6$\�þ�Y_\��)\����\�O�CH�+��|�_|�\�hժU\��\�[��þ�<Fxa��\�O�;\��ۥ߷�x�^\�\�_s\�1\��g��l;\�3�\�\�{lt\�\n\�\0\0\0h\�z��\�U��\�b��|>Ji��\�?蠃�p|ٲe�l۵k\���adwi]�\�\�\���Wn�w�R�F���|\�ܹ\�H�-�\�]w]\�\�\�\�\�\\s\�7���?N\�5V>fi}�b>�zϝ���4:�\�c��Un��\\��\�k�v�o�q�\���/?J�K�\��\0\0\0\0�a=S\��F�\'���g\Z�g�ma\n�\\E\�~�\�\�\�\��#\�/\������;\�d[�8k\���	�.\\8���\�\�\'7���<\�\��gϞ��~�?����1k֬d[�+׫��j�/�O�0�>��\����\�\��\�_X\0\0\0@\�\�3Jikk;���㨸\��o�|�?�Q�q�\�\�\�~v��\�ua\\����X,Ί\����o��u\\�O|���\��\r��\��0a}i:��\�lB\�\\��K\��\�>��\��l\�\rM?S�&\�\�!uiZ�\�$���ip�/_�\�6�9Jn\�{\�ï㬳Ϊ��!T_8u\�Qɔ6��t<\��,?z\���\��\�\\���\��a]��D\��\�\0\0\0h\��T�\�\���q=\Z׿ŵ4�\�\�Z\�ڸz\�\�\Z׮�~\�Ρ\�a�\��J\\\�\r\�/\��\�0�M\\�\�\�57<Ox��q}5�?��\�\��G��W\�\�\�\�³և\�^z\�I�*\��\rsч)hB`��\�~!l�\��!���\r\�8a�/�˓և\�\Z�8�!LM\�\�\�����/�^K�+>�\���\�\�\r\�}�p\��\�\rM\�S����Fz\ra\��\�\�>n��\n\�\0\0\0h\��\��\�P�=!��P����\�>+�O\���n� |��yxމ�\�\�~�\"�?\\dv\�y�U\�\'�ru�r\�\\%�\0\0\0 ����>w{�M�vp�_��\�Ee\�\�����OV\�o�\��Ҩya��\0\0\0\0���>\�\�ng�\'ů]>�_��T��&;�W\�z\0\0\0\0�r\��T��\�ӧO��S��?\�\�~\�z%�\0\0\0 ���>w�gΜ�[Y�=\�\Zև��u\�Y\�;\��g�yf�|�r�\�\�\0\0\0`\��T��\�Z�gIZ\��O<1	\�W�^��\�\��q\�g���\0\0\0\0�\'�O��{;�\��{�5��\�\���\��m�fΜ9<\���K.�6nܸ\�h��\�>;:��ӣx �=cƌd{�_�=o޼}F֯_�>�6mZt\�!�D\'�tR�bŊ�KX\0\0\0\0MBX�\�s����\�w��{�5���\�;� ��\��f͚���/m\�\�\�L����V�J�\�=�\�}\��E�E�6mJ*\�>��\�\�\'�pBr{Æ\r���\�~�B���\�SO��\0\0\0\0\�$��\�U,\�hkk�/Y�=\�|�\�0G}\�C`�*I�ևQ��Ta]yX�k׮\�\�9\�S�u�/N~�|�\��\�Y_z\�\�\�P﹄�\0\0\0\0\�$���>w�\���S��{�9�/Ui|)$/\�\�|��Ɔ��a݅^���\�+F\�W{.a=\0\0\0\04	a}z\����\���_��{�5��\�\�/?z\�\�^x!Y�\r\�:::�\��\�{\��\�g\�:묚a����ua>���\�o\�\�48a4�l\Z�z\�%�\0\0\0�&!�O��{.�ϟ��\�3�a}�>�\�ad�A�y\�\�}\�\�\�K�\�	|\r\�\��\���\��\�۷\'�և�ᢱ���[�.:\�3�@?L��$鹄�\0\0\0\0\�$���>w\�\��?��{fa\Z%�\0\0\0�����>wK�\�\����\�SX���\0\0\0\0d��>��\�bW{{�9Y�=��JX\0\0\0@\�	\�S}\�\�\��\�g��\�+a=\0\0\0\0�\'�O��{,����\�SX���\0\0\0\0d��>����?�\�K��{\n땰\0\0\0�\�֧W�X|0�\���{\n땰\0\0\0�\�֧�\�\�\���{\n땰\0\0\0�\�֧�\��C\\_\��\�)�W\�z\0\0\0\02OX�^�|�;\�b���\�z%�\0\0\0 ���>ww\���\�Y�=��JX\0\0\0@\�	\�\�+�\�3>��\�SX���\0\0\0\0d��>��\�\��|�����\�z%�\0\0\0 ���>w�\���Y�=��JX\0\0\0@\�	\�S}\�n�리��\�z%�\0\0\0 ��\�\�\�o,���\�SX���\0\0\0\0d��>��\�\��|�o��{\n땰\0\0\0�\�֧�\��E\\\�\��\�h\�\"A�\n����k��\0\0\0@&	\�\�+�\�ώ\�\�\�Y�=�.]\�\�\�\�/�n�\�\�\��aWW\�\�z>\0\0\0\0�$�O�b�xE>��N\�\�g�y\�\�%K�l{\�wv\n�[sD}껻�\�\��=\0\0\0�L֧�\�}9�h�ߵ�������\�\�4(a\�r\�R\��\�z\0\0\0\02MX�\�s�Ÿ\�w$\0\0\0\0\0RNX�^�|�P,t$\0\0\0\0\0R�P(,\����#�\�b�|>�ώ\0\0\0\0@�\n�Oǵ�^X�\�\�/p�\�!>W\�\�\�1G\0\0\0\0 e\n�u\��ӦM;\�QJ�����+�\�?\�H\0\0\0\0\0�\�\�\���a}�X�\�J��|����=\�H\0\0\0\0\0�P��\�7�>u\�qZ\\�	\0\0\0\0��6w��\�ӧ���\��\���#\0\0\0\0�R�|~�Q�\�\�\�\��\�\�\�\'�\0\0\0\0@J\���ϔ\�U�#�>���q�\�H\0\0\0\0\0�Xi\�z�\�\�)�ϟ��W	\0\0\0\0���>r$ҩ��\��\�7	\0\0\0����\�\�\�zk�ʕ+�hѢ���K5AuwwGK�.\�\��T���\na���N����z%\0\0\0@F� rٲeQ�g\�\�D500=�\�ۻ�����\����G�P8.���J\0\0\0��\n#��MH\�\�\�\�z]K\�?Tk�����O\n�^�\0\0\0 �\�\�B�殮���Z���Z�̜9�\n�\�f�\0\0\0 �\��\���#]T�P-\�?\�\�ڎ,\n\��J\0\0\0��\ZMٻuG�\�W%��\�H�\���\�z����~�j�-I�\�N�\�?&\�\�?����P(\�\0\0\0\0U/�ܽ{O�彝÷��\�7և\�\���m�Ѷ�w\n��-\�?J���=њ����5�TX�i\��\�D���\�)\n\�\0\0\0\0U+�A��\�\�毎�~eS�\�\�h\�#k�\���\�?�\�\�\��x�o<��h���0�5�Gymx�_�\�K��Յڰ�1Q\�+\n\�w=O\�\0\0\0ȠZa\�\�\�\�u�\�G���r�a�{\�CadK��z}�\�\�\�\�6mX��\0\�\��n\\\��y�\�	\0\0\0�1�\�\�\'V�4\�?����CadK��R�麹fX�i\��\�8���R@F\�\��\0\0\0�7\�|�\�\�Ν�׷\�z\�s\�\��5k\�ӧO�\��u�\�x\�׾{\�-�������\�~\�k_�O�Zk�F��oJa�5�=�\�_Dk��%�ú\��\'_67�0�����^�8\0*���\�\0\0\0\�\�\�n;���nziƌ��3gN�裏F�W������?�0*ٲeK�\�oDO=�T���=\n�_{\�\��\\>mڴ�\�\�Q-�\�ݺ#��l��!��\�/�+�\�>\�\'\�W�(�\�r���W�o�ևmڰ�1Fa}�@��\��\0\0\0\�L��|\�nXu饗�j���I?\Z|�A�|��\�k_�گf͚\�w\�e��G�5T#�MsF\�W\�\�U\�W�(�\�r���7��fX�i\��\�4\Z\�a\0\0\0\�dΜ9�����x\�?�\�?F���\�x=���\�e�]���W^�h[[\�o:\�\�&�F2��QY[zVGk��i�)p\�ua�6��R)�?o��\0\0\0&\��\�_�\�?�\�?��?�i4�\�{\�h\�ܹtvvnjkk��#�]��F2��Q�ֽp�~a}X��\��4֑�\�~\�\��\0\0\005BP?{�\�_��\�\'\�<�\�3flokk�ώx6տ�l_\��\r�\n���\�?�k�\�\�\�߳�8�M\�?\Z4\�)mL�\0\0\0S!L}F\�OfP_�\�c�\�1cF��\�T/�|juO\�in�տ��Qp(�l��\��w{��/��\��a[\�G;\�?F0\�\�d�\�\0\0\0\0��\rsԯZ�*:P�\��iSGGǿL�6\�`g [j����F7\�_\�pX\�\�/G�\���\�Ȗ\�a\�|\�\�D�<��5��R�}¾F\�\�54z1\�Fa\0\0\0\��\�_�J��\��\��\����lg [j���;wG�<�f2\�͂ގ��\��ay\�k�\�ӯlJB�����v	#��-\�?F\ZMo���1\n���H\\_\Z�9\Z��s\�Y\0\0\0�l�\�v۹�^z\�xX\�\�\��aGG\�\�b��qg\";\�M���w}�\�p\�RX�K뷾�SP/�l��\�\�h�z�\�i�cH\����C\\\�\�\�F�Z�\�\0\0�d�馛^z\�G��r\�w�Z,ow&���hU\�\�\�a}X\n#[��5�/�6�\�OUF\�Ge\�H`_ԗjf\�vs\�\0\0�d��\�?6cƌ_mٲe\�\������b��\�\��\�1��^	#���[�\0�Z�^/������\�o{\0\0\0�h�\�s\�\�s\�̉�ڟ�ɟ��X,��3�\r\�Ha$���1:�\���1�\�b�����%�\�\0\0��t뭷��p\�\�)\�\�\�\�\�i~��I\�Ha���\��S(�P�\���\�4x�F��\��\�\�?�3\�\0\0\0\�oΜ9�W���~\�ʕ�\n�\�bg��5\Z&	#����������Փ#|�3کhj���[P_b{\0\0\0��f\�����\�\�����\�B�\�\�4�F\�$a�0R�\�?�q��\�/����\�_-��Yn\�A�x_\0\0\0P2}��\�\�?��A%��\�\�h�$�F\����11\��˟���1ޑ\�\����%\�\0\0��\�BX���\�H�$�F\��G���s9%�����P��\�l�a\�\��?Z?\�z��yG\0\0\0�Qj���*%�F���\r�2�R���|>��`ڴi\�Z\ZY?\�\��F֗_tv���\0\0��h�9\�6����5\Z&MFX�\n�\�r�P�zhtꩧF\�=��0\�\��\���6�bŊd]�Y�~<}m��Q�ύ���pX_җ����ə�~,��9\�\0\0`�n��\�ͯ��ꔇ�+W�\\U(�9#ͭ\�0�\�\����x�\�d9��\�za}V�G�,d��׿����\�;\',�\�}+�\\NX\�h�YQ,/�җ\�H�jA}���7��M`?\�9�\0\0�\�\�[o}\�\'������\�Z^(�\�in��I�֗����\�\��\��>�OD�\�{cX>\�C�\�v�\�gG��~z�n˖-\�W\\v\�aɨ\�+��2ھ}{�m\�ڵ\�~\��\�\�\�U_O�c\rD3g\�L\'<\�%�\\mܸ1\��\�c�E��rJ�Xa[�_x���	\���Zm���N�>��\�%\�\�ϓO>y�6Z�=��\�\�i������\\sd��\�>�<nh�G}t��`L}�\�c5ڷ3\Z\�\�I_K�#\�k�������\�q\r\��\0\0\00~s\�\���m�\�6\�a�\�ٳ\�\n�i\�Hsk4L:Pa���5k\�$\�GuTC�|C(Y�mѢEѦM��u!�\�x\��h���\�r\�ös\�=w�9K\�*_O�cuvv&\�^|�\�hժU\�rx��\�\�#�Ln�[�.�\"$,�B�zۄ��G�6>k֬$�L�p���\�k��\�n\�\�ˢ\\��>�\�Mnذ!Y\�\�X�\\�\�j�o��\'�id�HA}��\����x.\��\0\0`\\s\�5��җ����L�����b������\�H6���|\�ATw���=�˖-\�gۮ]���agi]�\�м۵�U��j����\�;l;�\�3�G���\�ܹs��\�#m\�\��\�x\���n�i8\�.o��\�c#m;,�/���\�\�ˣ\\�)m\Z\�s��Ѿ-�\�O�������ؗ�OP\0\0\0\�\���%K�LYX�\�o��|>��\�DvLEX��6:/w�$WZ��8FhV�+�;�\�誫���;\�d�O<q\�m\�z��V;_��P�\�c�M�\�	�sU\��j\�Ѷ�\�	\�.w���X��ma}Ua�}\�4_\�5ԗT\�gV<�9\�\0\0`2\\��ū����~�\�\��vttl�>}�o;ְّ�4UH�48a:��\�\�/��\�C�\�\�\�:Y\�\�S��\�\�:+\�v\�g$�W�X�L��C\�:\�6a��Q��\��\�0�M\�z�)�\�C�\��\�\�\�j=V��\�Ȳ�1��.:[�7\�\�F�\�h/j\0\0\0�֬Y��\�Tڭ�޺�P(\�\�dKZ\��p�\�K/�4	C���t�\�0mG�{;�^�\�j\\8�T}}}I�_�XmDׯ_�l?/��\�d[x�0H�\�\�Hۄ��G�v�W�W\�r�I\�z\�1\\��\�SOM\�[�mW\�kaJ�p\�ʋЎ�\�\�z���~\"TN�����ƃ�\\��U~\0\0\0\0L�\�/���_v\�e��\�,��\�\�,��\�\�\���\�@�LFX?\�u\�GDw\�qG2�H7\�_3	8\���\���\��Qk���6\�\0\0\00Y���\��뮻��\�p6m\�4��ioo?ǑϞ,��a��·\��atr�l��F��!�oR\�Ot�\0\0\0�dڴi�\��Ѻ�\���\�\����?\�\�\�\�T,�\�g�0R���4��N]c\�\0\0\0�*\�b�\�_�\��<�\�#{&kD�PP���]\�Ha$���\�4J��hGƻ�,\0\0\0L�\�ӧ���3z\�ﾾ���>L}cD}�	#��\��GS\�T6��\0\0�fF\�wtt�ˬY��֯_�{<!�\�\�\�\�[o�uu���9\�[�0R���4�F�O\��~\\\�\0\0\0\�D���쎎�w�������k׎j�}___�ܹs��\��\�=mmm�鈶a�0�C�hJaJ��s���	\�r�z\0\0\0hN!d/\n7ǵ\��\�/�ٝw޹b�\�\�/���\�\�\�\��-[���r\�\�Uw\�u\��ٳg�)�;���߇iu\�\�\"�F�\�M�\�����&�(s�[o�5?�,�\�E�%\�@�������K�n�k��>��z\�\�\0�9�|�3C���q��ko\\\�P�Ƶ\"���kZgg\�G1a�F�\�M�2�7G},�7˖-������\���@�\�\�n\�\�\����@��z\�\�\0\0\�H%�D�\�?�M)��fNP��,�7�\r�\���u�\0\��޳\0��J���4��\���\�՞þ��\���L�\�Z9\�\�{6\0�0R	#\�?�4֣o\�\�)`�m�l\�\�{6\0\0>�\0���\��Ͳ\�ݺ#�ꃫ�\n\���~} �k\�<\�x���\r\0�0\�`��CX\�\�}s�\�=і�v\�^�z\�p��K\���\rF\�\�ߩ\�ד\�\\\�\�ٞd�^\�\�{6\0�0R�\0�(�CXߴ}3�7󖯋n��:z��Mђ\�z�9��p\�rX��K�\�\�}��\�Ϣ\�;�8��\�3׶y�\'Q�vIK\\\�\�{��l\0��\��M_�\\�I�P�����\0g���躇W6#\�\�?|9ڰ�=}I�s�ڨ\�\�\�霸ލ\�C?/�^\�^_\0Ȑ�K���o\�\�[===?L�\�\�?��!�?�A\��z\Zp�\�z}I�3�a��m�\�[\�?z����\�rX�hgu��R@F\�d9��^�/M�b���|>�*�m\\�\�\�>g�Uo\\+\�}�>\��\�x�S>}\0\�3\�<s\�%K���\�;;}\�l�\�!�\�\�\�^\�h��\�?���8a:�R@s\�C/G��\�ھmI�尮��ɗ{�\'�ϔ��\�\����9\�ˏ_�O�ua����\�	\�GZ\�\�{��TE{{�9�|~A��jWE8?R\�\r\�}\\�ӦM;\�\'1\0`Ruww_\�z>L\'>l���p.^\�\�U>�`�\r�CX�\�\0�w\�䢂\�����r���|^\�p�p_��\�\�@��^�����Rm|u������7B _\�~��z\���R\�\�P��\��Q��\��0\�ާ1\0\0`<�HY��a4P�ϡ��N�Sm\�0²r���ھ�\�d�\�\�z}�\�\�\�\�6mA;\�5\�gr���z}i\"�\�ۇF\�\�ӧO�n���h���ѫ��\Z���F�v튂>� \n\�`��\�?�y\�d�/~�\�B�\'\�\��m�\�\0\0�Q\�\��)�#%�\�y�H:	\�8��t�\�\�Z\�usͰ>l\�Z�����&i\��*\�}���\�7�\�\�;;;�y�\�%a�hF�>�h���|e��>���n�s�Of\0\0@Æ\�\�,�\�\�yG$����S#~���\n뵳Q\�H�p��s	\�\�\�Kc5\�/+\�\�̙����?�0z\�\'���e��~\\�.\0\0F64G\�ފQ@82�<�\��:ί/:\�\��Eþ��ρn��z����a}ئ-�l;\�6��\�{��4�\�L���\�݄�}����D\�\\sMy`�\�\�k\0\0���,�2\�\��\�O\�\�7\�<���\�\�\�\�__\�(h��6Z�7��fX�i-\�\�F��\�~\��*u}ih��\�Ͻ+W��&C�\�>\�g_>\�>~\�|J\0\0��6��T�?&.r�Rw>��#8��F7\�_\�p�s\�\�/G�\��-��\�F\�kK\�\�h\�\�7\�?N�.l\�Z��5z1\�F�~���z}i��}È�\�.F{\�m��\�\�?c\�\'5\0\0�\�?X��\�\�\nG(u\�SX?B�3�swt\�3o&\�,x\�\�h�k�\�k���\�=�ʦ$\�o\�\�h\�\�\�\��\�k���\���\�\�:\�\�\�Y\�\�a?ח�~�F\��R}\�Y\���\�h\����\�稟ȩojy\���\�.��|�\�o��\0\0T�5G՛�>�\�TX?B�S\nq�\���E\�EKNX.�\��\�N\�\�gJ\�\�p\�\�\�\�߳�8�=\�f+��\��!h���������\�\��\�{��4\Z\�\�\�\�>\�vvv��b���ӟ��򂳟�i\r\0\0(v\�\�Ks\�;R�:�\���\�\�ݺc8�	˂�O3�\��\�\�~��\�sևma\�!�\�\�T5ad|TV��\�A}�f�mO\��\�\����~��7o^t�}�\�_/����>�\0\0\�^\�%�&�Q���j��wG�o>��\�_\�\�K�	�\Ze�\�vV+@��\�\�\���*��n{\���R�\�<��\�]\�\��\�ӣ���֯]�����\�\�\��1�\�\0\0\0a��y�O\Z\�\�H�鍲o�v6\�\�d\r\�\r\�K�bJ�b�\�\�x�ח&J>�/���\�o�=�*_�\�W����_\�\0\0 \�u�2O�#�Icmd4}�Q�\�F�0�7���[���H��h���\�\�M\���0�+�\�\�{��4B?�˝0\�L�]͟?\�\���zȅf\0\0a��\�Z8��4�ѱ��\�6�����Hso\���ٝ��\�.����V`����%S:�}�k\�<Y-��^\�=���\��r\'޾��\�o�1ea�ʕ+\�G\�w�\�\0\0{��\�\�~�Q\�\�,\�I]:\�usq_e��\�_-��Yn\�A�x_τ�=�V�#��#\�\�5\�\�~_\�\�\�6�����NYX��\�o��\�^�\�\0\0\0a��Z\�1V\�z��vV#x\���=�\�\�O\�m�#٫�\�	\�K�d�Za}yFP\�G޳뵗�\�\�\�w\�\�5ea�|�\�k�\�\0\0h\�\��K�\�hc\�\�a�/���F�\�R���u�\�o�Vx��\�_���Q����\��\�\��))�\�{v#},|�S~{�	\�\0\0a��\�\�<ƪ�\�z_(e��~�\��\��ױq�\����\Z�\�3\��\��\��ø�\�\�/�\�]\�ua��x\�\�\���\���\r�\��\�+�뱎`�7������\�l#럏�\�ӦM;X?JO5ۗ<F\�\0\0\�^��Ip\�c,�o\�6\��\�\�ݻ\��J8;\�\�\�ơ�Į���X\�q�(\�o^\�C8߾#��\�u\�\��gC!~�\�pm\\q�\���C��oq����9\�\�\�7Ӝ����SՏN=�\�\�c�nݺT�\�\�\�o���\�\�Ɯ�\0\0��\�\����\�<\�\���n��Pj\�v�\'�[\�\��\��J�\�^-�s\��F���	\�\�;��D��q���2���~�\�/\��?��\�\�7�\�\�˝�/ؒ\�o��Ɣ��+W�\�\�\�O�\0\0@K��Y?\�<\�BԦPj�9\�ו.*\��\�\�\�>9\�]\Z\�^+��H�\����k 7EA�\�1�:��ҁ\�GW]uUr;;;��\�\�\��\�a\�a�����p�\�\�>�\�\��\�OO\�mٲ%�\�+�}=�\�\�\�+���o\�=��ɾ3f\�H��9sfr{޼y\���@r;\�\�w\�%�D7nL�\�u\�i�%\��\�/g=���\�N�\�\�\�~�\�ϟ��><w\�\�ۧW\0\0�\�?v����\�X�BI�-��ߟ\�??5���4�}���\�~���EA��\�4�M8P�(\�q��O|\":\�c����\�8��;�/\���E�=�X��\�\�~�\�m�-�6mڔ�A}X\��\�\�؇\�a��\�O�;\��\�6l�\�\�_�V�Z�,�{\�\�~�\�\�\�����?���~\�/w�\�\�\��\�\�\�>ea�5\�\\S��\�>\�\0\0u�PG�fo�\�8��h\�2kP2���\�F\'\��flo��@��C�N<�\�$�L\�t�\�j�w�5¾Ѡ�\��\��\��]���	\�>\�\�]w\�u\�\�\���\�Kn���t\\Cx*,�Q\�\�Vz���\����rJr{�\�\�\�ϓO>y���W\�>\�/����ӧO�\�/\�\n\�\��r�\�{\�M�|���\�\�\�}�\0\0\�L�\�\�F�m\�\�\�e���ZX?\�_(5k{+�0�FX>餓���	#\�+���R�񠾤Z`?�\�y�l���:P\�����\��\�se\�}�J F\�7����¶\��?��d۬Y��\�^xa�3�¯\�+_��~��\�\�y\�y\�\�\�s\�=\��\�S\0\00b\�\�(\�\�m�\�\�1i.\�z���\�(\�0��#��n�\�\�\�/N���>:���2\�)-_}�\�I��g��\�{k��\��!ך۸\�|\�B\�ѽ�N�J\�\�\�\�o�\�k�+�zsr�\�]\n-\�\�>\��ַ�\�a���{������\�Eg˃�F��\��\�Em�Ɓx�\�\�t\�AI?)��\���v�Zyۿ�����&˟�\�\�\Z\�V�|�~]>\rN\�ö\�\�Я\�χz(Y\�\�ё\��2L��\�:\�d[�\'74\rN\���\r\�˧\�	\�4|yq����$#�K\���\�|�\0\0F��\�@��#\�e\\o�\�\\\�\\\�a*�\�\����\�\��QGU3<\r��f͚}����χ\\kn\�z�!\�G�>:�_(����\�R`�\�k�+�zsr�5��|\�g&\�\�8\�\���\���B\�@;��\'\�3s���\Z���\"�{}E\�}�\�I��3g\�>_��\�\���뇗×C!\�_����jd[��\�^z\�\�S�B�.0���\�1\�}\�\��%V\��\�l\�}B\��+l[�vm\�C�7\�\�a}x_�ߣ_)\�>�\�\�o�\�\�>\\\�\�\'5\0\0���IXO&Ȱ~���\�MA��2�\�\�\���Z\�i���sU\�6�5���y\�h\Z\�[�¨\�+V��ݕ?o��\�x\���\�?�\�#��]\Zu��vVk���6Ŏ��	��B\�\�gM՗\�Gׇ�\��\�/&=�\�\'ʧ�ٛ\�\�O�I\r\0\0����8�\�Hs��ZTm}���k͇,�i�6���6{�\�dʜ\�|\�\�.W%��v�\�\�a}���\�}o�\�a�����\n\�\':�\��{}�*��\�6a��}\�]X\nϿ�կF����\�/_�|�\�o\�ҧ4\0\0�\��\�Ʉ\�42�q���sS\�כ۸\�|ȍ�a�\�\�4����x\�\�t9aJ��z�/���t\�T#��i�2\�\�\�;uM������z���춶�OƟyJ�m�\�}���ԇ\�\\T\0\0a=Yq��F\�2�7_pn\n\��zsך9\'�o�6����0\�\r]\0w�\�\��C�Q�a\�p�\�\�s\�\�?��\�`;+\��ڋ\�N\�{�\�\�{v>��L\\�0�|�^�DN}S1��ɶ��\��	\r\0\0h����\�~�Q\�\�l�S\�db\��H_�(\�b���\�R��\\�r\�B����覛n*\��\0\0�\�\�\�(��6�R\�\�\Z\r\�?\��q]\�^\�K�\�\�\�~N�����p��\�oO�\�\�h���\�\�=�\�S9�>��FP\0\0�����h�*u\�,Li�~��\�6a�@.cA�~�/M�b�xR�xCE\��ٟ�Y�\��F˖-�~�_\�k~�p>�\�\�\����o\\{]L\0\0a=%�\�FU*\�Y�����F?җ&C�^,o�e?�\�\��\�:ŧ1\0\0`\\��p�\�GU�mg��|��z�H_�ӧO�\����\��m�\�\��\�\�\�\�8ͧ0\0\0`B\�\�(��6�R\�\�J�7s\��#}i2uvv~th>��\r��2MNo�X\�ޝ\�\���\�}�\0\0&����h�*�\�\��|>^�y\�땾\0\0�R\�z�B�#�\�FU���0\"8�\�\��^��%\0\0�֓��6�Z��utt�=\�\�^\�K\0\0\0)&�\'+8�mT�j;�1c\�1�\��M\�땾\0\0�b\�z�B�#�\�FU�����\��Z\��J_\0\0H1a=Y!��h��U\�Y�X�ݸ^m���E�i�\�_�⾴\�\'\0\0�Q֓�P!�6�Zxd�\�\�\�_h����K�����k�M\\===?��\�\�>�\0\0\0������\nQ�Q\�\�#\�� �{�\��^�\�3\�\\�dɒm\��\�Nm��Fԇ����{}\\_�\�\0\0`���d� T����Vmg\�\�\�\��ŮVy�\�\�\� >Wχ�V\�9SMS\�|�.�\0\0#a=Y!�6;sm�g{��\�\�\�\��K\0\0\0\0)&�\'+��\��fg�m�lO�|>\�V,��\0\0\0\0RLXOV\��\�\�\\\�\�ٞ,�|~f�X�G	\0\0\0 ń�d��^X�\�\�6\��$�-�<�\��K\0\0\0\0)&�\'+\�n.qhU�|�ϋ\�\�]�\0\0\0�����0��ġ���_\��\0\0\0���\�z2�|\�\���[~s\\79\0\0\0�\�\�\�\��KZ�o�at�#\0\0\0\����\0\����[q]\�H\0\0\0@���/��t�-�n\\�;\0\0\0�\�\�\� \�\�\�닎\0\0\0���\�z\0H�\����|�͑\0\0\0�t�_X\0\��[�0�\�_\�H\0\0\0@���/��t�-\�noo?Ǒ\0\0\0�t�_X\0\��[�\�\�\�\�g:\0\0\0�\�\�\� \�\�\�\�\����\0\0\0���\�z\0H�b��j[[\�Ɏ\0\0\0���\0R�������	\0\0\0H�?���\0�\�\�f̘q�#\0\0\0\����\0R,�Ͽ\�\�\��1G\0\0\0RLX\0��[�����\0\0\0���\�z\0H��\�\�\�0\0\0\0@\�	\� ��0�ޑ\0\0\0��\�@zutt�-\�\�H\0\0\0\�r�(:���ޚ�r\�\�_.Z�(\�\�\�Ju��>\�C�\�\�\�h\�ҥ�㚡�\�*f̘qL��|�#\0\0@\�	A��eˢ���hϞ=��j`` z�\�g�wwwAK�ttt�N�PX\�H\0\0\0\�rzA}S�{���^\�Rh\�b�w\�zՑ\0\0\0�儩o�\�\�]]]]{�T\0ZAGG\�\�\n�	\0\0\0ZN�] \��a��\�\��\�\�\n�w$\0\0\0h9�	\�{��ત² ]X\0�����b�\�\�H\0\0\0\�r\���w��s��\�\���\���\\Z߿m0\���N���\0ƥX,~�P(,t$\0\0\0h9�\��\�\�[�.�a�\�\�\�W6EK^\�\�<�f8�\�a�\�/m�����\�S?��\�\�\�`\���|[�X|ؑ\0\0\0�\�\�\n\�7o��{x�p8?R]�×�\r�\��\�`\����\�b����\0\0\0-�\�48O�\�i8��\'\�\�\�z\0�B�py\\\�u$\0\0\0h9�\��0�M)��桗�~��hm߶�\�rXW\��\�\�=�ua=\0�K>���b�x�#\0\0@˩\��nݑ\\@�|��\�W\�֕\�a\�\�+`\��X\n�\�\�Ñ\0\0\0�\�T\�MsF\�W\�\�U\�W�.���(\n7\�u�#\0\0@\�\�\��Y�Q�at�#\0\0@\�1\r��\0�E�P�V\\W9\0\0\0����\�k��a_���\0ƣP(|7�\�	\0\0\0ZN����\�=U���V��\�F���\0ƥP(\�\�	\0\0\0ZN��~���\���\�}�\�۠p]X\0cV(\�\���6G\0\0��S+�ܹ;�\�7��n��v���}\�\�K^덞~eS\�߷tm�k�nẰ\0ƬP(,\�\��8\0\0\0��z\�\����\�\�__06\\@�և\�\���\�\�\�\�`\�\n�Bw{{�9�\0\0\0-�^X_Y�[w��aY�.���T(�����LG\0\0��3��^	\�`2\n��vtt��#\0\0@\�֧?�/\n�\�\���f\0ҮX,�\�\�\�v�#\0\0@\�֧7�!}�_\\{\�2��ԋ���\�\�\�v�#\0\0@\�֧/��\�K�5�v�߳����w�\�\n\08.�!������\�(\"#R\�UD��/����X��\�\�zȂ�;Qc�b\�\�\�a\�\\0o|>�`fwfV\�廏7�\�\�\��y\'\0\0\0h9b}\�\��\�\�\�wa��\�^��Y<���X,��N\0\0\0\�r\��ƈ��4�^��\�<�={(�\�x\'\0\0\0h9b}\�\�z\�0\�h�\�3\0\0\0\�z����\�\��\�\n���\0\0\0\0\�db\��Ǘ�4:::�\\.�T*�W���������������n0;33�}�Px/\�\0\0\0\04���>l�\�����\�\�6r��y�i�^�\0\0\0\0hp�c}m?̰۷��\���|\�\�ٙtuu%SSS\�\�\�ŋ�;;;K\�\�\�\�k�/�\�jz\\\�g3��t��%\�\�\�P��\�\�B]\������U�f\0\0\0\0�;\��ۃ��\�~�XL�\�\�GGG\��\�\�ċ󲎛��L�C���\�mς{\�5B\��\�\�\�f]\�z\0\0\0\0\0\Z\\\�5\�\�3\�CX\�=�Fx\�yl\�:��ܗf��\�\Z\�h\�\0\0\0\0x+�g\�/--%\�\�\�\�\�\�֋H�u^\�q�6\�g]C�\0\0\0\0\�\�Ŏ�!�\�r����79??Ofgg\�疗�?-�322�⼬\�j\�\�\�\�\��m��@ؿ��K_7\�\Za9���ep\�\�\�\�z\0\0\0\0\0\�N=\�`6�\�瓫��t-�pc\�0�=D�p\�\�\�y\�\�\�\�v\�q���\���pz�ف��\�\�\� }|ee%�R \\cuu5�\Z\'\'\'i\�\�X\\\\\�\0\0\0\0x;1b�׌���4����5\�\�+\�\0\0\0\0�j�\�\�\�Ǔ���dmmM�\0\0\0\0��4J�o\�!\�\0\0\0\049�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 2�^�\0\0\0\0 �J�\"�\��x(�J��\0\0\0\04����\�nnnD�:���?�J�>�\0\0\0\0\0Ml{{�ǝ��߯���\�\�kF}�\�r�\�q�\�\n\0\0\0\0\�\�\�\���R闰\�JXݨ�~�z\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0^\�/!c��\rQ�\0\0\0\0IEND�B`�',1);
/*!40000 ALTER TABLE `act_ge_bytearray` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ge_property`
--

DROP TABLE IF EXISTS `act_ge_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ge_property`
--

LOCK TABLES `act_ge_property` WRITE;
/*!40000 ALTER TABLE `act_ge_property` DISABLE KEYS */;
INSERT INTO `act_ge_property` VALUES ('next.dbid','1450001',581),('schema.history','create(5.20.0.1)',1),('schema.version','5.20.0.1',1);
/*!40000 ALTER TABLE `act_ge_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_actinst`
--

DROP TABLE IF EXISTS `act_hi_actinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_actinst`
--

LOCK TABLES `act_hi_actinst` WRITE;
/*!40000 ALTER TABLE `act_hi_actinst` DISABLE KEYS */;
INSERT INTO `act_hi_actinst` VALUES ('1132584','oms_proposal_review:1:1135004','1137501','1137501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-09-18 21:06:07','2017-09-18 21:06:07',1,''),('1132585','oms_proposal_review:1:1135004','1137501','1137501','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-09-18 21:06:07','2017-09-18 21:06:07',25,''),('1132586','oms_proposal_review:1:1135004','1137501','1137501','reviewproposaltask','1132587',NULL,'Pricing Review','userTask','9','2017-09-18 21:06:07','2017-09-18 21:08:24',137464,''),('1132592','oms_proposal_review:1:1135004','1137501','1137501','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-09-18 21:08:24','2017-09-18 21:08:24',24,''),('1132593','oms_proposal_review:1:1135004','1137501','1137501','mpreviewtask','1132594',NULL,'Media Planner Review','userTask','410','2017-09-18 21:08:24','2017-09-18 21:08:49',24753,''),('1132596','oms_proposal_review:1:1135004','1137501','1137501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-09-18 21:08:49','2017-09-18 21:08:49',0,''),('1132597','oms_proposal_review:1:1135004','1137501','1137501','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-09-18 21:08:49','2017-09-18 21:08:49',23,''),('1132598','oms_proposal_review:1:1135004','1137501','1137501','adminReview','1132599',NULL,'Admin Review','userTask','2','2017-09-18 21:08:49','2017-09-18 21:09:41',51680,''),('1132630','oms_proposal_review:1:1135004','1137501','1137501','exclusivegateway2',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-09-18 21:09:41','2017-09-18 21:09:41',1,''),('1132631','oms_proposal_review:1:1135004','1137501','1137501','proposalApprovedTask',NULL,NULL,'Approved','serviceTask',NULL,'2017-09-18 21:09:41','2017-09-18 21:09:41',22,''),('1132633','oms_proposal_review:1:1135004','1137501','1137501','omsProposalEndEvent',NULL,NULL,'ProcessEnd','endEvent',NULL,'2017-09-18 21:09:41','2017-09-18 21:09:41',0,''),('1132635','oms_proposal_review:1:1135004','1132634','1132634','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-09-18 22:40:14','2017-09-18 22:40:14',8,''),('1132643','oms_proposal_review:1:1135004','1132634','1132634','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-09-18 22:40:14','2017-09-18 22:40:14',33,''),('1132644','oms_proposal_review:1:1135004','1132634','1132634','mpreviewtask','1132645',NULL,'Media Planner Review','userTask','410','2017-09-18 22:40:14',NULL,NULL,''),('1132648','oms_proposal_review:1:1135004','1132647','1132647','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-09-20 02:06:00','2017-09-20 02:06:00',36,''),('1132656','oms_proposal_review:1:1135004','1132647','1132647','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-09-20 02:06:00','2017-09-20 02:06:00',59,''),('1132657','oms_proposal_review:1:1135004','1132647','1132647','mpreviewtask','1132658',NULL,'Media Planner Review','userTask','410','2017-09-20 02:06:00',NULL,NULL,''),('1132661','oms_proposal_review:1:1135004','1132660','1132660','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-09-21 23:15:41','2017-09-21 23:15:41',87,''),('1132669','oms_proposal_review:1:1135004','1132660','1132660','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-09-21 23:15:41','2017-09-21 23:15:41',32,''),('1132670','oms_proposal_review:1:1135004','1132660','1132660','mpreviewtask','1132671',NULL,'Media Planner Review','userTask','410','2017-09-21 23:15:41','2017-09-21 23:16:37',55894,''),('1132674','oms_proposal_review:1:1135004','1132660','1132660','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-09-21 23:16:37','2017-09-21 23:16:37',0,''),('1132675','oms_proposal_review:1:1135004','1132660','1132660','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-09-21 23:16:37','2017-09-21 23:16:37',23,''),('1132676','oms_proposal_review:1:1135004','1132660','1132660','reviewproposaltask','1132677',NULL,'Pricing Review','userTask','1','2017-09-21 23:16:37','2017-09-21 23:17:07',30029,''),('1132684','oms_proposal_review:1:1135004','1132660','1132660','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-09-21 23:17:07','2017-09-21 23:17:07',24,''),('1132685','oms_proposal_review:1:1135004','1132660','1132660','mpreviewtask','1132686',NULL,'Media Planner Review','userTask','410','2017-09-21 23:17:07','2017-09-21 23:17:31',24012,''),('1132688','oms_proposal_review:1:1135004','1132660','1132660','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-09-21 23:17:31','2017-09-21 23:17:31',0,''),('1132689','oms_proposal_review:1:1135004','1132660','1132660','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-09-21 23:17:31','2017-09-21 23:17:31',26,''),('1132690','oms_proposal_review:1:1135004','1132660','1132660','reviewproposaltask','1132691',NULL,'Pricing Review','userTask',NULL,'2017-09-21 23:17:31',NULL,NULL,''),('1137502','oms_proposal_review:1:1135004','1137501','1137501','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-09-18 11:04:00','2017-09-18 11:04:00',307,''),('1137510','oms_proposal_review:1:1135004','1137501','1137501','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-09-18 11:04:00','2017-09-18 11:04:00',73,''),('1137511','oms_proposal_review:1:1135004','1137501','1137501','mpreviewtask','1137512',NULL,'Media Planner Review','userTask','410','2017-09-18 11:04:00','2017-09-18 11:05:12',71942,''),('1137515','oms_proposal_review:1:1135004','1137501','1137501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-09-18 11:05:12','2017-09-18 11:05:12',44,''),('1137516','oms_proposal_review:1:1135004','1137501','1137501','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-09-18 11:05:12','2017-09-18 11:05:12',79,''),('1137517','oms_proposal_review:1:1135004','1137501','1137501','reviewproposaltask','1137518',NULL,'Pricing Review','userTask','1','2017-09-18 11:05:12','2017-09-18 11:06:27',75238,''),('1137525','oms_proposal_review:1:1135004','1137501','1137501','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-09-18 11:06:27','2017-09-18 11:06:27',108,''),('1137526','oms_proposal_review:1:1135004','1137501','1137501','mpreviewtask','1137527',NULL,'Media Planner Review','userTask','410','2017-09-18 11:06:27','2017-09-18 21:06:07',35980212,''),('1145002','oms_proposal_review:3:1142504','1145001','1145001','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-05 23:41:41','2017-10-05 23:41:42',87,''),('1145010','oms_proposal_review:3:1142504','1145001','1145001','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-05 23:41:42','2017-10-05 23:41:42',21,''),('1145011','oms_proposal_review:3:1142504','1145001','1145001','mpreviewtask','1145012',NULL,'Media Planner Review','userTask','410','2017-10-05 23:41:42',NULL,NULL,''),('1145015','oms_proposal_review:3:1142504','1145014','1145014','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-06 03:44:33','2017-10-06 03:44:33',55,''),('1145023','oms_proposal_review:3:1142504','1145014','1145014','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-06 03:44:33','2017-10-06 03:44:34',35,''),('1145024','oms_proposal_review:3:1142504','1145014','1145014','mpreviewtask','1145025',NULL,'Media Planner Review','userTask','410','2017-10-06 03:44:34','2017-10-06 03:52:01',446935,''),('1145029','oms_proposal_review:3:1142504','1145014','1145014','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 03:52:01','2017-10-06 03:52:01',19,''),('1145030','oms_proposal_review:3:1142504','1145014','1145014','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-06 03:52:01','2017-10-06 03:52:01',55,''),('1145031','oms_proposal_review:3:1142504','1145014','1145014','reviewproposaltask','1145032',NULL,'Pricing Review','userTask','1','2017-10-06 03:52:01','2017-10-06 03:53:19',77645,''),('1145039','oms_proposal_review:3:1142504','1145014','1145014','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-06 03:53:19','2017-10-06 03:53:19',18,''),('1145040','oms_proposal_review:3:1142504','1145014','1145014','mpreviewtask','1145041',NULL,'Media Planner Review','userTask','410','2017-10-06 03:53:19','2017-10-06 03:53:59',39802,''),('1145043','oms_proposal_review:3:1142504','1145014','1145014','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 03:53:59','2017-10-06 03:53:59',0,''),('1145044','oms_proposal_review:3:1142504','1145014','1145014','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-06 03:53:59','2017-10-06 03:53:59',18,''),('1145045','oms_proposal_review:3:1142504','1145014','1145014','reviewproposaltask','1145046',NULL,'Pricing Review','userTask','9','2017-10-06 03:53:59','2017-10-06 03:54:29',29729,''),('1145051','oms_proposal_review:3:1142504','1145014','1145014','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-06 03:54:29','2017-10-06 03:54:29',20,''),('1145052','oms_proposal_review:3:1142504','1145014','1145014','mpreviewtask','1145053',NULL,'Media Planner Review','userTask','410','2017-10-06 03:54:29','2017-10-06 03:54:51',21790,''),('1145055','oms_proposal_review:3:1142504','1145014','1145014','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 03:54:51','2017-10-06 03:54:51',0,''),('1145056','oms_proposal_review:3:1142504','1145014','1145014','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-06 03:54:51','2017-10-06 03:54:51',41,''),('1145057','oms_proposal_review:3:1142504','1145014','1145014','adminReview','1145058',NULL,'Admin Review','userTask','2','2017-10-06 03:54:51','2017-10-06 03:57:14',142839,''),('1145091','oms_proposal_review:3:1142504','1145014','1145014','exclusivegateway2',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 03:57:14','2017-10-06 03:57:14',1,''),('1145092','oms_proposal_review:3:1142504','1145014','1145014','proposalApprovedTask',NULL,NULL,'Approved','serviceTask',NULL,'2017-10-06 03:57:14','2017-10-06 03:57:14',16,''),('1145094','oms_proposal_review:3:1142504','1145014','1145014','omsProposalEndEvent',NULL,NULL,'ProcessEnd','endEvent',NULL,'2017-10-06 03:57:14','2017-10-06 03:57:14',0,''),('1145096','oms_proposal_review:3:1142504','1145095','1145095','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-06 03:58:49','2017-10-06 03:58:49',37,''),('1145104','oms_proposal_review:3:1142504','1145095','1145095','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-06 03:58:49','2017-10-06 03:58:49',34,''),('1145105','oms_proposal_review:3:1142504','1145095','1145095','mpreviewtask','1145106',NULL,'Media Planner Review','userTask','410','2017-10-06 03:58:49','2017-10-06 04:00:59',129616,''),('1145110','oms_proposal_review:3:1142504','1145095','1145095','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 04:00:59','2017-10-06 04:00:59',0,''),('1145111','oms_proposal_review:3:1142504','1145095','1145095','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-06 04:00:59','2017-10-06 04:00:59',26,''),('1145112','oms_proposal_review:3:1142504','1145095','1145095','reviewproposaltask','1145113',NULL,'Pricing Review','userTask','1','2017-10-06 04:00:59','2017-10-06 04:01:40',40600,''),('1145120','oms_proposal_review:3:1142504','1145095','1145095','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-06 04:01:40','2017-10-06 04:01:40',18,''),('1145121','oms_proposal_review:3:1142504','1145095','1145095','mpreviewtask','1145122',NULL,'Media Planner Review','userTask','410','2017-10-06 04:01:40','2017-10-06 04:02:12',32418,''),('1145124','oms_proposal_review:3:1142504','1145095','1145095','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 04:02:12','2017-10-06 04:02:12',1,''),('1145125','oms_proposal_review:3:1142504','1145095','1145095','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-06 04:02:12','2017-10-06 04:02:12',16,''),('1145126','oms_proposal_review:3:1142504','1145095','1145095','reviewproposaltask','1145127',NULL,'Pricing Review','userTask','9','2017-10-06 04:02:12','2017-10-06 04:03:50',98002,''),('1145132','oms_proposal_review:3:1142504','1145095','1145095','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-06 04:03:50','2017-10-06 04:03:50',18,''),('1145133','oms_proposal_review:3:1142504','1145095','1145095','mpreviewtask','1145134',NULL,'Media Planner Review','userTask','410','2017-10-06 04:03:50','2017-10-06 04:07:50',240331,''),('1145136','oms_proposal_review:3:1142504','1145095','1145095','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-06 04:07:50','2017-10-06 04:07:50',0,''),('1145137','oms_proposal_review:3:1142504','1145095','1145095','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-06 04:07:50','2017-10-06 04:07:50',17,''),('1145138','oms_proposal_review:3:1142504','1145095','1145095','adminReview','1145139',NULL,'Admin Review','userTask','2','2017-10-06 04:07:50',NULL,NULL,''),('1227513','oms_proposal_review:63:1295004','1227512','1227512','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-07 12:37:11','2017-10-07 12:37:11',79,''),('1227521','oms_proposal_review:63:1295004','1227512','1227512','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-07 12:37:11','2017-10-07 12:37:11',16,''),('1227522','oms_proposal_review:63:1295004','1227512','1227512','mpreviewtask','1227523',NULL,'Media Planner Review','userTask','410','2017-10-07 12:37:11','2017-10-07 12:37:36',25187,''),('1227527','oms_proposal_review:63:1295004','1227512','1227512','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 12:37:36','2017-10-07 12:37:36',0,''),('1227528','oms_proposal_review:63:1295004','1227512','1227512','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 12:37:36','2017-10-07 12:37:36',28,''),('1227529','oms_proposal_review:63:1295004','1227512','1227512','reviewproposaltask','1227530',NULL,'Pricing Review','userTask','1','2017-10-07 12:37:36','2017-10-07 12:38:23',46977,''),('1227537','oms_proposal_review:63:1295004','1227512','1227512','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 12:38:23','2017-10-07 12:38:23',29,''),('1227538','oms_proposal_review:63:1295004','1227512','1227512','mpreviewtask','1227539',NULL,'Media Planner Review','userTask','410','2017-10-07 12:38:23','2017-10-07 12:38:51',27586,''),('1227541','oms_proposal_review:63:1295004','1227512','1227512','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 12:38:51','2017-10-07 12:38:51',0,''),('1227542','oms_proposal_review:63:1295004','1227512','1227512','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 12:38:51','2017-10-07 12:38:51',89,''),('1227543','oms_proposal_review:63:1295004','1227512','1227512','reviewproposaltask','1227544',NULL,'Pricing Review','userTask','9','2017-10-07 12:38:51','2017-10-07 13:08:10',1758849,''),('1322507','oms_proposal_review:63:1295004','1227512','1227512','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 13:08:10','2017-10-07 13:08:10',75,''),('1322508','oms_proposal_review:63:1295004','1227512','1227512','mpreviewtask','1322509',NULL,'Media Planner Review','userTask','410','2017-10-07 13:08:10','2017-10-07 13:08:37',26715,''),('1322511','oms_proposal_review:63:1295004','1227512','1227512','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 13:08:37','2017-10-07 13:08:37',16,''),('1322512','oms_proposal_review:63:1295004','1227512','1227512','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-07 13:08:37','2017-10-07 13:08:37',52,''),('1322513','oms_proposal_review:63:1295004','1227512','1227512','adminReview','1322514',NULL,'Admin Review','userTask','2','2017-10-07 13:08:37',NULL,NULL,''),('1322553','oms_proposal_review:75:1325004','1322552','1322552','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-07 13:17:30','2017-10-07 13:17:30',88,''),('1322561','oms_proposal_review:75:1325004','1322552','1322552','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-07 13:17:30','2017-10-07 13:17:30',16,''),('1322562','oms_proposal_review:75:1325004','1322552','1322552','mpreviewtask','1322563',NULL,'Media Planner Review','userTask','410','2017-10-07 13:17:30','2017-10-07 13:18:05',34575,''),('1322567','oms_proposal_review:75:1325004','1322552','1322552','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 13:18:05','2017-10-07 13:18:05',1,''),('1322568','oms_proposal_review:75:1325004','1322552','1322552','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 13:18:05','2017-10-07 13:18:05',55,''),('1322569','oms_proposal_review:75:1325004','1322552','1322552','reviewproposaltask','1322570',NULL,'Pricing Review','userTask','1','2017-10-07 13:18:05','2017-10-07 13:18:44',39019,''),('1322577','oms_proposal_review:75:1325004','1322552','1322552','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 13:18:44','2017-10-07 13:18:44',145,''),('1322578','oms_proposal_review:75:1325004','1322552','1322552','mpreviewtask','1322579',NULL,'Media Planner Review','userTask','410','2017-10-07 13:18:44','2017-10-07 13:19:18',33716,''),('1322581','oms_proposal_review:75:1325004','1322552','1322552','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 13:19:18','2017-10-07 13:19:18',1,''),('1322582','oms_proposal_review:75:1325004','1322552','1322552','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 13:19:18','2017-10-07 13:19:18',44,''),('1322583','oms_proposal_review:75:1325004','1322552','1322552','reviewproposaltask','1322584',NULL,'Pricing Review','userTask','9','2017-10-07 13:19:18','2017-10-07 14:00:55',2497440,''),('1322595','oms_proposal_review:93:1370004','1322594','1322594','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-07 15:09:42','2017-10-07 15:09:42',21,''),('1322603','oms_proposal_review:93:1370004','1322594','1322594','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-07 15:09:42','2017-10-07 15:09:42',15,''),('1322604','oms_proposal_review:93:1370004','1322594','1322594','mpreviewtask','1322605',NULL,'Media Planner Review','userTask','410','2017-10-07 15:09:42','2017-10-07 15:10:05',23124,''),('1322609','oms_proposal_review:93:1370004','1322594','1322594','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 15:10:05','2017-10-07 15:10:05',0,''),('1322610','oms_proposal_review:93:1370004','1322594','1322594','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 15:10:05','2017-10-07 15:10:05',49,''),('1322611','oms_proposal_review:93:1370004','1322594','1322594','reviewproposaltask','1322612',NULL,'Pricing Review','userTask','1','2017-10-07 15:10:05','2017-10-07 15:13:12',187094,''),('1322619','oms_proposal_review:93:1370004','1322594','1322594','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 15:13:12','2017-10-07 15:13:12',50,''),('1322620','oms_proposal_review:93:1370004','1322594','1322594','mpreviewtask','1322621',NULL,'Media Planner Review','userTask','410','2017-10-07 15:13:12','2017-10-07 15:14:00',48289,''),('1322623','oms_proposal_review:93:1370004','1322594','1322594','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 15:14:00','2017-10-07 15:14:00',0,''),('1322624','oms_proposal_review:93:1370004','1322594','1322594','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 15:14:00','2017-10-07 15:14:00',54,''),('1322625','oms_proposal_review:93:1370004','1322594','1322594','reviewproposaltask','1322626',NULL,'Pricing Review','userTask','9','2017-10-07 15:14:00','2017-10-07 15:16:18',138441,''),('1322631','oms_proposal_review:93:1370004','1322594','1322594','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 15:16:18','2017-10-07 15:16:19',307,''),('1322632','oms_proposal_review:93:1370004','1322594','1322594','mpreviewtask','1322633',NULL,'Media Planner Review','userTask','410','2017-10-07 15:16:19','2017-10-07 15:17:29',69778,''),('1322635','oms_proposal_review:93:1370004','1322594','1322594','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 15:17:29','2017-10-07 15:17:29',1,''),('1322636','oms_proposal_review:93:1370004','1322594','1322594','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-07 15:17:29','2017-10-07 15:17:29',73,''),('1322637','oms_proposal_review:93:1370004','1322594','1322594','adminReview','1322638',NULL,'Admin Review','userTask','2','2017-10-07 15:17:29',NULL,NULL,''),('1325006','oms_proposal_review:75:1325004','1325005','1325005','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-07 13:47:29','2017-10-07 13:47:29',23,''),('1325014','oms_proposal_review:75:1325004','1325005','1325005','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-07 13:47:29','2017-10-07 13:47:29',11,''),('1325015','oms_proposal_review:75:1325004','1325005','1325005','mpreviewtask','1325016',NULL,'Media Planner Review','userTask','411','2017-10-07 13:47:29','2017-10-07 13:47:58',29148,''),('1325020','oms_proposal_review:75:1325004','1325005','1325005','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 13:47:58','2017-10-07 13:47:58',15,''),('1325021','oms_proposal_review:75:1325004','1325005','1325005','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 13:47:58','2017-10-07 13:47:58',24,''),('1325022','oms_proposal_review:75:1325004','1325005','1325005','reviewproposaltask','1325023',NULL,'Pricing Review','userTask','1','2017-10-07 13:47:58','2017-10-07 13:48:34',36345,''),('1325030','oms_proposal_review:75:1325004','1325005','1325005','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 13:48:34','2017-10-07 13:48:34',16,''),('1325031','oms_proposal_review:75:1325004','1325005','1325005','mpreviewtask','1325032',NULL,'Media Planner Review','userTask','411','2017-10-07 13:48:34','2017-10-07 14:00:38',724162,''),('1325034','oms_proposal_review:75:1325004','1325005','1325005','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 14:00:38','2017-10-07 14:00:38',0,''),('1325035','oms_proposal_review:75:1325004','1325005','1325005','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-07 14:00:38','2017-10-07 14:00:38',16,''),('1325036','oms_proposal_review:75:1325004','1325005','1325005','reviewproposaltask','1325037',NULL,'Pricing Review','userTask',NULL,'2017-10-07 14:00:38',NULL,NULL,''),('1325041','oms_proposal_review:75:1325004','1322552','1322552','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-07 14:00:55','2017-10-07 14:00:55',16,''),('1325042','oms_proposal_review:75:1325004','1322552','1322552','mpreviewtask','1325043',NULL,'Media Planner Review','userTask','410','2017-10-07 14:00:55','2017-10-07 14:01:16',21444,''),('1325045','oms_proposal_review:75:1325004','1322552','1322552','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-07 14:01:16','2017-10-07 14:01:16',1,''),('1325046','oms_proposal_review:75:1325004','1322552','1322552','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-07 14:01:16','2017-10-07 14:01:16',15,''),('1325047','oms_proposal_review:75:1325004','1322552','1322552','adminReview','1325048',NULL,'Admin Review','userTask',NULL,'2017-10-07 14:01:16',NULL,NULL,''),('1435002','oms_proposal_review:1:1432504','1435001','1435001','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-09 19:08:33','2017-10-09 19:08:33',52,''),('1435010','oms_proposal_review:1:1432504','1435001','1435001','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-09 19:08:33','2017-10-09 19:08:33',62,''),('1435011','oms_proposal_review:1:1432504','1435001','1435001','mpreviewtask','1435012',NULL,'Media Planner Review','userTask','410','2017-10-09 19:08:33','2017-10-09 19:08:41',8496,''),('1435016','oms_proposal_review:1:1432504','1435001','1435001','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 19:08:41','2017-10-09 19:08:42',15,''),('1435017','oms_proposal_review:1:1432504','1435001','1435001','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-09 19:08:42','2017-10-09 19:08:42',68,''),('1435018','oms_proposal_review:1:1432504','1435001','1435001','reviewproposaltask','1435019',NULL,'Pricing Review','userTask','1','2017-10-09 19:08:42','2017-10-09 19:09:24',41650,''),('1435026','oms_proposal_review:1:1432504','1435001','1435001','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-09 19:09:24','2017-10-09 19:09:24',70,''),('1435027','oms_proposal_review:1:1432504','1435001','1435001','mpreviewtask','1435028',NULL,'Media Planner Review','userTask','410','2017-10-09 19:09:24','2017-10-09 19:09:54',30455,''),('1435030','oms_proposal_review:1:1432504','1435001','1435001','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 19:09:54','2017-10-09 19:09:54',0,''),('1435031','oms_proposal_review:1:1432504','1435001','1435001','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-09 19:09:54','2017-10-09 19:09:54',34,''),('1435032','oms_proposal_review:1:1432504','1435001','1435001','reviewproposaltask','1435033',NULL,'Pricing Review','userTask','9','2017-10-09 19:09:54','2017-10-09 19:10:39',44908,''),('1435038','oms_proposal_review:1:1432504','1435001','1435001','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-09 19:10:39','2017-10-09 19:10:39',119,''),('1435039','oms_proposal_review:1:1432504','1435001','1435001','mpreviewtask','1435040',NULL,'Media Planner Review','userTask','410','2017-10-09 19:10:39','2017-10-09 19:11:15',36069,''),('1435042','oms_proposal_review:1:1432504','1435001','1435001','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 19:11:15','2017-10-09 19:11:15',1,''),('1435043','oms_proposal_review:1:1432504','1435001','1435001','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-09 19:11:15','2017-10-09 19:11:15',60,''),('1435044','oms_proposal_review:1:1432504','1435001','1435001','adminReview','1435045',NULL,'Admin Review','userTask','2','2017-10-09 19:11:15','2017-10-09 19:12:23',67642,''),('1435078','oms_proposal_review:1:1432504','1435001','1435001','exclusivegateway2',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 19:12:23','2017-10-09 19:12:23',0,''),('1435079','oms_proposal_review:1:1432504','1435001','1435001','proposalApprovedTask',NULL,NULL,'Approved','serviceTask',NULL,'2017-10-09 19:12:23','2017-10-09 19:12:23',299,''),('1435081','oms_proposal_review:1:1432504','1435001','1435001','omsProposalEndEvent',NULL,NULL,'ProcessEnd','endEvent',NULL,'2017-10-09 19:12:23','2017-10-09 19:12:23',0,''),('1437502','oms_proposal_review:1:1432504','1437501','1437501','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-09 20:35:47','2017-10-09 20:35:47',44,''),('1437510','oms_proposal_review:1:1432504','1437501','1437501','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-09 20:35:47','2017-10-09 20:35:47',55,''),('1437511','oms_proposal_review:1:1432504','1437501','1437501','mpreviewtask','1437512',NULL,'Media Planner Review','userTask','410','2017-10-09 20:35:47','2017-10-09 20:35:55',8397,''),('1437516','oms_proposal_review:1:1432504','1437501','1437501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 20:35:55','2017-10-09 20:35:55',16,''),('1437517','oms_proposal_review:1:1432504','1437501','1437501','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-09 20:35:55','2017-10-09 20:35:56',139,''),('1437518','oms_proposal_review:1:1432504','1437501','1437501','reviewproposaltask','1437519',NULL,'Pricing Review','userTask','1','2017-10-09 20:35:56','2017-10-09 20:36:37',41461,''),('1437526','oms_proposal_review:1:1432504','1437501','1437501','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-09 20:36:37','2017-10-09 20:36:38',116,''),('1437527','oms_proposal_review:1:1432504','1437501','1437501','mpreviewtask','1437528',NULL,'Media Planner Review','userTask','410','2017-10-09 20:36:38','2017-10-09 20:37:13',34832,''),('1437530','oms_proposal_review:1:1432504','1437501','1437501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 20:37:13','2017-10-09 20:37:13',0,''),('1437531','oms_proposal_review:1:1432504','1437501','1437501','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-09 20:37:13','2017-10-09 20:37:13',85,''),('1437532','oms_proposal_review:1:1432504','1437501','1437501','reviewproposaltask','1437533',NULL,'Pricing Review','userTask','9','2017-10-09 20:37:13','2017-10-09 20:38:06',53179,''),('1437538','oms_proposal_review:1:1432504','1437501','1437501','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-09 20:38:06','2017-10-09 20:38:06',129,''),('1437539','oms_proposal_review:1:1432504','1437501','1437501','mpreviewtask','1437540',NULL,'Media Planner Review','userTask','410','2017-10-09 20:38:06','2017-10-09 20:38:48',42482,''),('1437542','oms_proposal_review:1:1432504','1437501','1437501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 20:38:48','2017-10-09 20:38:48',1,''),('1437543','oms_proposal_review:1:1432504','1437501','1437501','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-09 20:38:48','2017-10-09 20:38:49',67,''),('1437544','oms_proposal_review:1:1432504','1437501','1437501','adminReview','1437545',NULL,'Admin Review','userTask','2','2017-10-09 20:38:49','2017-10-09 20:39:36',46632,''),('1437578','oms_proposal_review:1:1432504','1437501','1437501','exclusivegateway2',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 20:39:36','2017-10-09 20:39:36',1,''),('1437579','oms_proposal_review:1:1432504','1437501','1437501','proposalApprovedTask',NULL,NULL,'Approved','serviceTask',NULL,'2017-10-09 20:39:36','2017-10-09 20:39:36',432,''),('1437581','oms_proposal_review:1:1432504','1437501','1437501','omsProposalEndEvent',NULL,NULL,'ProcessEnd','endEvent',NULL,'2017-10-09 20:39:37','2017-10-09 20:39:37',0,''),('1440002','oms_proposal_review:1:1432504','1440001','1440001','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-09 21:09:41','2017-10-09 21:09:41',17,''),('1440010','oms_proposal_review:1:1432504','1440001','1440001','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-09 21:09:41','2017-10-09 21:09:41',16,''),('1440011','oms_proposal_review:1:1432504','1440001','1440001','mpreviewtask','1440012',NULL,'Media Planner Review','userTask','410','2017-10-09 21:09:41','2017-10-09 21:09:49',8232,''),('1440016','oms_proposal_review:1:1432504','1440001','1440001','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 21:09:49','2017-10-09 21:09:49',0,''),('1440017','oms_proposal_review:1:1432504','1440001','1440001','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-09 21:09:49','2017-10-09 21:09:49',59,''),('1440018','oms_proposal_review:1:1432504','1440001','1440001','reviewproposaltask','1440019',NULL,'Pricing Review','userTask','1','2017-10-09 21:09:49','2017-10-09 21:10:42',53283,''),('1440026','oms_proposal_review:1:1432504','1440001','1440001','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-09 21:10:42','2017-10-09 21:10:42',80,''),('1440027','oms_proposal_review:1:1432504','1440001','1440001','mpreviewtask','1440028',NULL,'Media Planner Review','userTask','410','2017-10-09 21:10:42','2017-10-09 21:11:15',32953,''),('1440030','oms_proposal_review:1:1432504','1440001','1440001','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 21:11:15','2017-10-09 21:11:15',1,''),('1440031','oms_proposal_review:1:1432504','1440001','1440001','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-09 21:11:15','2017-10-09 21:11:15',42,''),('1440032','oms_proposal_review:1:1432504','1440001','1440001','reviewproposaltask','1440033',NULL,'Pricing Review','userTask','9','2017-10-09 21:11:15','2017-10-09 21:12:08',52611,''),('1440038','oms_proposal_review:1:1432504','1440001','1440001','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-09 21:12:08','2017-10-09 21:12:08',153,''),('1440039','oms_proposal_review:1:1432504','1440001','1440001','mpreviewtask','1440040',NULL,'Media Planner Review','userTask','410','2017-10-09 21:12:08','2017-10-09 21:12:42',33510,''),('1440042','oms_proposal_review:1:1432504','1440001','1440001','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 21:12:42','2017-10-09 21:12:42',1,''),('1440043','oms_proposal_review:1:1432504','1440001','1440001','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-09 21:12:42','2017-10-09 21:12:42',202,''),('1440044','oms_proposal_review:1:1432504','1440001','1440001','adminReview','1440045',NULL,'Admin Review','userTask','2','2017-10-09 21:12:42','2017-10-09 21:13:48',65926,''),('1440078','oms_proposal_review:1:1432504','1440001','1440001','exclusivegateway2',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-09 21:13:48','2017-10-09 21:13:48',0,''),('1440079','oms_proposal_review:1:1432504','1440001','1440001','proposalApprovedTask',NULL,NULL,'Approved','serviceTask',NULL,'2017-10-09 21:13:48','2017-10-09 21:13:48',571,''),('1440081','oms_proposal_review:1:1432504','1440001','1440001','omsProposalEndEvent',NULL,NULL,'ProcessEnd','endEvent',NULL,'2017-10-09 21:13:49','2017-10-09 21:13:49',0,''),('1442502','oms_proposal_review:1:1432504','1442501','1442501','omsproposalstartevent',NULL,NULL,'Start','startEvent',NULL,'2017-10-10 09:49:54','2017-10-10 09:49:54',51,''),('1442510','oms_proposal_review:1:1432504','1442501','1442501','servicetask1',NULL,NULL,'In Progress','serviceTask',NULL,'2017-10-10 09:49:54','2017-10-10 09:49:54',24,''),('1442511','oms_proposal_review:1:1432504','1442501','1442501','mpreviewtask','1442512',NULL,'Media Planner Review','userTask','410','2017-10-10 09:49:54','2017-10-10 09:51:02',68385,''),('1442516','oms_proposal_review:1:1432504','1442501','1442501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-10 09:51:02','2017-10-10 09:51:02',23,''),('1442517','oms_proposal_review:1:1432504','1442501','1442501','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-10 09:51:02','2017-10-10 09:51:02',61,''),('1442518','oms_proposal_review:1:1432504','1442501','1442501','reviewproposaltask','1442519',NULL,'Pricing Review','userTask','1','2017-10-10 09:51:02','2017-10-10 09:54:32',210444,''),('1445003','oms_proposal_review:1:1432504','1442501','1442501','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-10 09:54:32','2017-10-10 09:54:32',41,''),('1445004','oms_proposal_review:1:1432504','1442501','1442501','mpreviewtask','1445005',NULL,'Media Planner Review','userTask','410','2017-10-10 09:54:32','2017-10-10 09:55:18',46341,''),('1445007','oms_proposal_review:1:1432504','1442501','1442501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-10 09:55:18','2017-10-10 09:55:18',3,''),('1445008','oms_proposal_review:1:1432504','1442501','1442501','proposalPriceReviewTask',NULL,NULL,'Proposal Price Review Service','serviceTask',NULL,'2017-10-10 09:55:18','2017-10-10 09:55:18',75,''),('1445009','oms_proposal_review:1:1432504','1442501','1442501','reviewproposaltask','1445010',NULL,'Pricing Review','userTask','9','2017-10-10 09:55:18','2017-10-10 09:56:15',56553,''),('1445015','oms_proposal_review:1:1432504','1442501','1442501','reviewservicetask',NULL,NULL,'Review Service','serviceTask',NULL,'2017-10-10 09:56:15','2017-10-10 09:56:15',54,''),('1445016','oms_proposal_review:1:1432504','1442501','1442501','mpreviewtask','1445017',NULL,'Media Planner Review','userTask','410','2017-10-10 09:56:15','2017-10-10 09:57:00',45344,''),('1445020','oms_proposal_review:1:1432504','1442501','1442501','exclusivegateway1',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-10 09:57:00','2017-10-10 09:57:00',1,''),('1445021','oms_proposal_review:1:1432504','1442501','1442501','adminReviewTask',NULL,NULL,'Admin review Service','serviceTask',NULL,'2017-10-10 09:57:00','2017-10-10 09:57:00',71,''),('1445022','oms_proposal_review:1:1432504','1442501','1442501','adminReview','1445023',NULL,'Admin Review','userTask','2','2017-10-10 09:57:00','2017-10-10 09:57:41',40829,''),('1445056','oms_proposal_review:1:1432504','1442501','1442501','exclusivegateway2',NULL,NULL,'Exclusive Gateway','exclusiveGateway',NULL,'2017-10-10 09:57:41','2017-10-10 09:57:41',0,''),('1445057','oms_proposal_review:1:1432504','1442501','1442501','proposalApprovedTask',NULL,NULL,'Approved','serviceTask',NULL,'2017-10-10 09:57:41','2017-10-10 09:57:41',208,''),('1445059','oms_proposal_review:1:1432504','1442501','1442501','omsProposalEndEvent',NULL,NULL,'ProcessEnd','endEvent',NULL,'2017-10-10 09:57:41','2017-10-10 09:57:41',0,'');
/*!40000 ALTER TABLE `act_hi_actinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_attachment`
--

DROP TABLE IF EXISTS `act_hi_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_attachment`
--

LOCK TABLES `act_hi_attachment` WRITE;
/*!40000 ALTER TABLE `act_hi_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_comment`
--

DROP TABLE IF EXISTS `act_hi_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_comment`
--

LOCK TABLES `act_hi_comment` WRITE;
/*!40000 ALTER TABLE `act_hi_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_detail`
--

DROP TABLE IF EXISTS `act_hi_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_detail`
--

LOCK TABLES `act_hi_detail` WRITE;
/*!40000 ALTER TABLE `act_hi_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_identitylink`
--

DROP TABLE IF EXISTS `act_hi_identitylink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_identitylink`
--

LOCK TABLES `act_hi_identitylink` WRITE;
/*!40000 ALTER TABLE `act_hi_identitylink` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_identitylink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_procinst`
--

DROP TABLE IF EXISTS `act_hi_procinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_procinst`
--

LOCK TABLES `act_hi_procinst` WRITE;
/*!40000 ALTER TABLE `act_hi_procinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_procinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_taskinst`
--

DROP TABLE IF EXISTS `act_hi_taskinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `CLAIM_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_taskinst`
--

LOCK TABLES `act_hi_taskinst` WRITE;
/*!40000 ALTER TABLE `act_hi_taskinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_taskinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_varinst`
--

DROP TABLE IF EXISTS `act_hi_varinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_varinst`
--

LOCK TABLES `act_hi_varinst` WRITE;
/*!40000 ALTER TABLE `act_hi_varinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_varinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_group`
--

DROP TABLE IF EXISTS `act_id_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_group`
--

LOCK TABLES `act_id_group` WRITE;
/*!40000 ALTER TABLE `act_id_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_info`
--

DROP TABLE IF EXISTS `act_id_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_info`
--

LOCK TABLES `act_id_info` WRITE;
/*!40000 ALTER TABLE `act_id_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_membership`
--

DROP TABLE IF EXISTS `act_id_membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_membership`
--

LOCK TABLES `act_id_membership` WRITE;
/*!40000 ALTER TABLE `act_id_membership` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_user`
--

DROP TABLE IF EXISTS `act_id_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_user`
--

LOCK TABLES `act_id_user` WRITE;
/*!40000 ALTER TABLE `act_id_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_procdef_info`
--

DROP TABLE IF EXISTS `act_procdef_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_procdef_info`
--

LOCK TABLES `act_procdef_info` WRITE;
/*!40000 ALTER TABLE `act_procdef_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_procdef_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_deployment`
--

DROP TABLE IF EXISTS `act_re_deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_deployment`
--

LOCK TABLES `act_re_deployment` WRITE;
/*!40000 ALTER TABLE `act_re_deployment` DISABLE KEYS */;
INSERT INTO `act_re_deployment` VALUES ('1447501','SpringAutoDeployment',NULL,'','2017-10-10 06:02:37');
/*!40000 ALTER TABLE `act_re_deployment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_model`
--

DROP TABLE IF EXISTS `act_re_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_model`
--

LOCK TABLES `act_re_model` WRITE;
/*!40000 ALTER TABLE `act_re_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_re_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_procdef`
--

DROP TABLE IF EXISTS `act_re_procdef`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_procdef`
--

LOCK TABLES `act_re_procdef` WRITE;
/*!40000 ALTER TABLE `act_re_procdef` DISABLE KEYS */;
INSERT INTO `act_re_procdef` VALUES ('oms_proposal_review:1:1447504',1,'http://www.activiti.org/test','My process','oms_proposal_review',1,'1447501','D:\\Projects\\Hotstar\\hotstar\\oms\\oms-media\\target\\classes\\processes\\oms_proposal_review.bpmn','D:\\Projects\\Hotstar\\hotstar\\oms\\oms-media\\target\\classes\\processes\\oms_proposal_review.oms_proposal_review.png',NULL,0,1,1,'');
/*!40000 ALTER TABLE `act_re_procdef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_event_subscr`
--

DROP TABLE IF EXISTS `act_ru_event_subscr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_event_subscr`
--

LOCK TABLES `act_ru_event_subscr` WRITE;
/*!40000 ALTER TABLE `act_ru_event_subscr` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_event_subscr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_execution`
--

DROP TABLE IF EXISTS `act_ru_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_execution`
--

LOCK TABLES `act_ru_execution` WRITE;
/*!40000 ALTER TABLE `act_ru_execution` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_identitylink`
--

DROP TABLE IF EXISTS `act_ru_identitylink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_identitylink`
--

LOCK TABLES `act_ru_identitylink` WRITE;
/*!40000 ALTER TABLE `act_ru_identitylink` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_identitylink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_job`
--

DROP TABLE IF EXISTS `act_ru_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_job`
--

LOCK TABLES `act_ru_job` WRITE;
/*!40000 ALTER TABLE `act_ru_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_task`
--

DROP TABLE IF EXISTS `act_ru_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_task`
--

LOCK TABLES `act_ru_task` WRITE;
/*!40000 ALTER TABLE `act_ru_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_variable`
--

DROP TABLE IF EXISTS `act_ru_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_variable`
--

LOCK TABLES `act_ru_variable` WRITE;
/*!40000 ALTER TABLE `act_ru_variable` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_variable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_unit`
--

DROP TABLE IF EXISTS `ad_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_unit` (
  `ad_unit_ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `DISPLAY_NAME` varchar(100) DEFAULT NULL,
  `IS_ACTIVE` bit(1) DEFAULT b'0',
  `VERSION` int(11) DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `MODIFIED_BY` varchar(100) DEFAULT NULL,
  `MODIFIED_DATE` date DEFAULT NULL,
  `CAPACITY` decimal(10,0) DEFAULT NULL,
  `WEIGHT` decimal(10,0) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ad_unit_ID`),
  KEY `ad_unit_customer_idx` (`CUSTOMER_ID`),
  CONSTRAINT `ad_unit_customer` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_unit`
--

LOCK TABLES `ad_unit` WRITE;
/*!40000 ALTER TABLE `ad_unit` DISABLE KEYS */;
INSERT INTO `ad_unit` VALUES (203,'Banking','Bank','',NULL,'4',NULL,NULL,NULL,NULL,NULL,'2017-07-04 10:36:02','\0','2017-07-04 10:36:02',4,NULL),(204,'Finance','Fin Corp','',NULL,'4',NULL,NULL,NULL,NULL,NULL,'2017-07-04 10:36:10','\0','2017-07-04 10:36:10',4,NULL),(205,'Entertainment','Entertain','',NULL,'4',NULL,NULL,NULL,NULL,NULL,'2017-07-04 10:36:24','\0','2017-07-04 10:36:24',4,NULL),(206,'Gaming','Gaming','\0',NULL,'4',NULL,NULL,NULL,NULL,NULL,'2017-07-04 10:36:34','\0','2017-07-04 11:21:14',4,NULL);
/*!40000 ALTER TABLE `ad_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_target_type`
--

DROP TABLE IF EXISTS `audience_target_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_target_type` (
  `TARGET_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `Status` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TARGET_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_target_type`
--

LOCK TABLES `audience_target_type` WRITE;
/*!40000 ALTER TABLE `audience_target_type` DISABLE KEYS */;
INSERT INTO `audience_target_type` VALUES (1,'Age','',NULL,NULL,'\0',NULL,NULL),(2,'Gender','',NULL,NULL,'\0',NULL,NULL),(3,'States','',NULL,NULL,'\0',NULL,NULL),(4,'Countries','',NULL,NULL,'\0',NULL,NULL),(5,'Device','',NULL,NULL,'\0',NULL,NULL),(6,'Browser','',NULL,NULL,'\0',NULL,NULL),(7,'OS','',NULL,NULL,'\0',NULL,NULL),(8,'Frequency cap','',NULL,NULL,'\0',NULL,NULL),(9,'Zip codes','',NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `audience_target_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_target_values`
--

DROP TABLE IF EXISTS `audience_target_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_target_values` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TARGET_TYPE_ID` bigint(20) DEFAULT NULL,
  `VALUE` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`ID`),
  KEY `target_idx` (`TARGET_TYPE_ID`),
  CONSTRAINT `target` FOREIGN KEY (`TARGET_TYPE_ID`) REFERENCES `audience_target_type` (`TARGET_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_target_values`
--

LOCK TABLES `audience_target_values` WRITE;
/*!40000 ALTER TABLE `audience_target_values` DISABLE KEYS */;
INSERT INTO `audience_target_values` VALUES (1,1,'18-24',''),(2,1,'25-39',''),(3,1,'40-49',''),(4,1,'50-59',''),(5,1,'60-69',''),(6,1,'70+',''),(7,2,'Male',''),(8,2,'Female',''),(9,3,'UP',''),(10,3,'Delhi',''),(11,3,'Punjab',''),(12,4,'USA',''),(13,4,'India',''),(14,4,'Australia',''),(15,4,'France',''),(16,5,'Apple',''),(17,5,'Samsung',''),(18,6,'Chrome',''),(19,6,'Firefox',''),(20,6,'Safari',''),(21,6,'IE',''),(22,7,'Windows',''),(23,7,'Mac',''),(24,8,'Days',''),(25,8,'Lifetime',''),(26,8,'Month',''),(27,8,'Week',''),(28,8,'Hours','');
/*!40000 ALTER TABLE `audience_target_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_record`
--

DROP TABLE IF EXISTS `audit_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_record` (
  `AUDIT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(45) NOT NULL,
  `ACTION_TYPE` varchar(100) DEFAULT NULL,
  `ACTION_DATE` date DEFAULT NULL,
  `METHOD_NAME` varchar(100) DEFAULT NULL,
  `CLASS_NAME` varchar(200) DEFAULT NULL,
  `METHOD_ARGUMENTS` varchar(1000) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`AUDIT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1946 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_record`
--

LOCK TABLES `audit_record` WRITE;
/*!40000 ALTER TABLE `audit_record` DISABLE KEYS */;
INSERT INTO `audit_record` VALUES (1536,'kapil.kumar','add new product','2017-09-18','ProductController.addProduct(..)','com.oms.controller.ProductController','com.oms.model.Product@9ea3a934','2017-09-18 20:21:16',NULL,'\0','2017-09-18 20:21:16',NULL),(1537,'kapil.kumar','add new product','2017-09-18','ProductController.addProduct(..)','com.oms.controller.ProductController','com.oms.model.Product@36f45614','2017-09-18 20:24:11',NULL,'\0','2017-09-18 20:24:11',NULL),(1538,'kapil.kumar','add new product','2017-09-18','ProductController.addProduct(..)','com.oms.controller.ProductController','com.oms.model.Product@92aee714','2017-09-18 20:26:23',NULL,'\0','2017-09-18 20:26:23',NULL),(1539,'ritesh.nailwal','Add new opportunity','2017-09-18','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@1ecfcd94','2017-09-18 20:51:32',NULL,'\0','2017-09-18 20:51:32',NULL),(1540,'ritesh.nailwal','upload opportunity document','2017-09-18','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@3ea212dc','2017-09-18 20:51:32',NULL,'\0','2017-09-18 20:51:32',NULL),(1541,'ritesh.nailwal','Add new opportunity','2017-09-18','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@a9b2fb94','2017-09-18 20:51:44',NULL,'\0','2017-09-18 20:51:44',NULL),(1542,'ritesh.nailwal','upload opportunity document','2017-09-18','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@27005b45','2017-09-18 20:51:44',NULL,'\0','2017-09-18 20:51:44',NULL),(1543,'sumit.chaturvedi','update proposal','2017-09-18','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@80f47e0f','2017-09-18 20:55:45',NULL,'\0','2017-09-18 20:55:45',NULL),(1544,'sumit.chaturvedi','update proposal','2017-09-18','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@8404f107','2017-09-18 20:56:38',NULL,'\0','2017-09-18 20:56:38',NULL),(1545,'sumit.chaturvedi','upload proposal document','2017-09-18','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@353fbe59','2017-09-18 20:56:38',NULL,'\0','2017-09-18 20:56:38',NULL),(1546,'sumit.chaturvedi','update proposal','2017-09-18','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@866a131c','2017-09-18 20:57:20',NULL,'\0','2017-09-18 20:57:20',NULL),(1547,'sumit.chaturvedi','upload proposal document','2017-09-18','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@5974093c','2017-09-18 20:57:20',NULL,'\0','2017-09-18 20:57:20',NULL),(1548,'sumit.chaturvedi','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 11:05:12',NULL,'\0','2017-09-18 11:05:12',NULL),(1549,'sumit.chaturvedi','execute task','2017-09-18','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 11:05:12',NULL,'\0','2017-09-18 11:05:12',NULL),(1550,'sumit.chaturvedi','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 11:05:32',NULL,'\0','2017-09-18 11:05:32',NULL),(1551,'vikas.parashar','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 11:06:27',NULL,'\0','2017-09-18 11:06:27',NULL),(1552,'vikas.parashar','execute task','2017-09-18','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 11:06:28',NULL,'\0','2017-09-18 11:06:28',NULL),(1553,'sumit.chaturvedi','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:06:07',NULL,'\0','2017-09-18 21:06:07',NULL),(1554,'sumit.chaturvedi','execute task','2017-09-18','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:06:07',NULL,'\0','2017-09-18 21:06:07',NULL),(1555,'paras.mishra','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:08:18',NULL,'\0','2017-09-18 21:08:18',NULL),(1556,'paras.mishra','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:08:24',NULL,'\0','2017-09-18 21:08:24',NULL),(1557,'paras.mishra','execute task','2017-09-18','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:08:25',NULL,'\0','2017-09-18 21:08:25',NULL),(1558,'sumit.chaturvedi','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:08:49',NULL,'\0','2017-09-18 21:08:49',NULL),(1559,'sumit.chaturvedi','execute task','2017-09-18','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:08:49',NULL,'\0','2017-09-18 21:08:49',NULL),(1560,'ritesh.nailwal','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:09:36',NULL,'\0','2017-09-18 21:09:36',NULL),(1561,'ritesh.nailwal','assign task','2017-09-18','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:09:41',NULL,'\0','2017-09-18 21:09:41',NULL),(1562,'ritesh.nailwal','execute task','2017-09-18','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1137501','2017-09-18 21:09:41',NULL,'\0','2017-09-18 21:09:41',NULL),(1563,'ritesh.nailwal','Add rate card','2017-09-18','RateCardController.addNewRateCard(..)','com.oms.controller.RateCardController','com.oms.model.RateCard@19db3a74','2017-09-18 21:13:38',NULL,'\0','2017-09-18 21:13:38',NULL),(1564,'ritesh.nailwal','Add new opportunity','2017-09-18','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@f0cc4f14','2017-09-18 22:14:46',NULL,'\0','2017-09-18 22:14:46',NULL),(1565,'ritesh.nailwal','upload opportunity document','2017-09-18','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@4b470585','2017-09-18 22:14:46',NULL,'\0','2017-09-18 22:14:46',NULL),(1566,'ritesh.nailwal','Add new opportunity','2017-09-18','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@d861d4','2017-09-18 22:14:51',NULL,'\0','2017-09-18 22:14:51',NULL),(1567,'ritesh.nailwal','upload opportunity document','2017-09-18','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@1755a4e9','2017-09-18 22:14:51',NULL,'\0','2017-09-18 22:14:51',NULL),(1568,'sumit.chaturvedi','Add new opportunity','2017-09-18','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@ae6354','2017-09-18 22:36:10',NULL,'\0','2017-09-18 22:36:10',NULL),(1569,'sumit.chaturvedi','upload opportunity document','2017-09-18','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@12034c1a','2017-09-18 22:36:10',NULL,'\0','2017-09-18 22:36:10',NULL),(1570,'sumit.chaturvedi','Add new opportunity','2017-09-18','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@c063a1b4','2017-09-18 22:36:36',NULL,'\0','2017-09-18 22:36:36',NULL),(1571,'sumit.chaturvedi','upload opportunity document','2017-09-18','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@792f878c','2017-09-18 22:36:36',NULL,'\0','2017-09-18 22:36:36',NULL),(1572,'sumit.chaturvedi','update proposal','2017-09-18','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@d3ddfd8d','2017-09-18 22:38:19',NULL,'\0','2017-09-18 22:38:19',NULL),(1573,'sumit.chaturvedi','update proposal','2017-09-18','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@d84de264','2017-09-18 22:39:37',NULL,'\0','2017-09-18 22:39:37',NULL),(1574,'sumit.chaturvedi','Add rate card','2017-09-19','RateCardController.addNewRateCard(..)','com.oms.controller.RateCardController','com.oms.model.RateCard@9bafa054','2017-09-19 02:33:00',NULL,'\0','2017-09-19 02:33:00',NULL),(1575,'sumit.chaturvedi','update rate card','2017-09-19','RateCardController.updateRateCard(..)','com.oms.controller.RateCardController','com.oms.model.RateCard@1c6fdce','2017-09-19 02:56:38',NULL,'\0','2017-09-19 02:56:38',NULL),(1576,'sumit.chaturvedi','update rate card','2017-09-19','RateCardController.updateRateCard(..)','com.oms.controller.RateCardController','com.oms.model.RateCard@1c6fdce','2017-09-19 02:57:13',NULL,'\0','2017-09-19 02:57:13',NULL),(1577,'ritesh.nailwal','Add new proposal','2017-09-20','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@12df102f','2017-09-20 02:02:32',NULL,'\0','2017-09-20 02:02:32',NULL),(1578,'ritesh.nailwal','upload proposal document','2017-09-20','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@228b2ea0','2017-09-20 02:02:32',NULL,'\0','2017-09-20 02:02:32',NULL),(1579,'ritesh.nailwal','update proposal','2017-09-20','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@d9895cf4','2017-09-20 02:02:44',NULL,'\0','2017-09-20 02:02:44',NULL),(1580,'ritesh.nailwal','update proposal','2017-09-20','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@dc32d07c','2017-09-20 02:03:31',NULL,'\0','2017-09-20 02:03:31',NULL),(1581,'ritesh.nailwal','update rate card','2017-09-21','RateCardController.updateRateCard(..)','com.oms.controller.RateCardController','com.oms.model.RateCard@1c6fc36','2017-09-21 23:01:56',NULL,'\0','2017-09-21 23:01:56',NULL),(1582,'ritesh.nailwal','update proposal','2017-09-21','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@e6ab72ed','2017-09-21 23:10:10',NULL,'\0','2017-09-21 23:10:10',NULL),(1583,'ritesh.nailwal','update proposal','2017-09-21','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@f86e8efa','2017-09-21 23:15:19',NULL,'\0','2017-09-21 23:15:19',NULL),(1584,'sumit.chaturvedi','assign task','2017-09-21','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:16:37',NULL,'\0','2017-09-21 23:16:37',NULL),(1585,'sumit.chaturvedi','execute task','2017-09-21','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:16:37',NULL,'\0','2017-09-21 23:16:37',NULL),(1586,'sumit.chaturvedi','assign task','2017-09-21','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:16:45',NULL,'\0','2017-09-21 23:16:45',NULL),(1587,'vikas.parashar','assign task','2017-09-21','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:17:07',NULL,'\0','2017-09-21 23:17:07',NULL),(1588,'vikas.parashar','execute task','2017-09-21','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:17:07',NULL,'\0','2017-09-21 23:17:07',NULL),(1589,'sumit.chaturvedi','assign task','2017-09-21','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:17:31',NULL,'\0','2017-09-21 23:17:31',NULL),(1590,'sumit.chaturvedi','execute task','2017-09-21','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1132660','2017-09-21 23:17:31',NULL,'\0','2017-09-21 23:17:31',NULL),(1591,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@7d3b9254','2017-09-23 01:09:08',NULL,'\0','2017-09-23 01:09:08',NULL),(1592,'ritesh.nailwal','upload opportunity document','2017-09-23','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@552e538f','2017-09-23 01:09:08',NULL,'\0','2017-09-23 01:09:08',NULL),(1593,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@c9b95174','2017-09-23 01:09:24',NULL,'\0','2017-09-23 01:09:24',NULL),(1594,'ritesh.nailwal','upload opportunity document','2017-09-23','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@5e4aec30','2017-09-23 01:09:24',NULL,'\0','2017-09-23 01:09:24',NULL),(1595,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@7519034','2017-09-23 01:12:08',NULL,'\0','2017-09-23 01:12:08',NULL),(1596,'ritesh.nailwal','upload opportunity document','2017-09-23','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@6a53aa1e','2017-09-23 01:12:08',NULL,'\0','2017-09-23 01:12:08',NULL),(1597,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@3affdcf4','2017-09-23 01:26:57',NULL,'\0','2017-09-23 01:26:57',NULL),(1598,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@a523dab4','2017-09-23 01:27:55',NULL,'\0','2017-09-23 01:27:55',NULL),(1599,'ritesh.nailwal','upload opportunity document','2017-09-23','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@68315afe','2017-09-23 01:27:55',NULL,'\0','2017-09-23 01:27:55',NULL),(1600,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@771d5bf4','2017-09-23 01:36:49',NULL,'\0','2017-09-23 01:36:49',NULL),(1601,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@f7fbbe34','2017-09-23 01:37:00',NULL,'\0','2017-09-23 01:37:00',NULL),(1602,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@3cbd01d4','2017-09-23 01:37:02',NULL,'\0','2017-09-23 01:37:02',NULL),(1603,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@1d20c114','2017-09-23 02:23:08',NULL,'\0','2017-09-23 02:23:08',NULL),(1604,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@6022da14','2017-09-23 02:24:51',NULL,'\0','2017-09-23 02:24:51',NULL),(1605,'ritesh.nailwal','Add new opportunity','2017-09-23','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@395fcd4','2017-09-23 02:39:01',NULL,'\0','2017-09-23 02:39:01',NULL),(1606,'ritesh.nailwal','User registration','2017-10-05','AuthenticationController.addUser(..)','com.oms.controller.AuthenticationController','428','2017-10-05 23:10:30',NULL,'\0','2017-10-05 23:10:30',NULL),(1607,'ritesh.nailwal','Add new proposal','2017-10-05','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@d0105366','2017-10-05 23:36:42',NULL,'\0','2017-10-05 23:36:42',NULL),(1608,'ritesh.nailwal','upload proposal document','2017-10-05','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@684b93b6','2017-10-05 23:36:43',NULL,'\0','2017-10-05 23:36:43',NULL),(1609,'ritesh.nailwal','update proposal','2017-10-05','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@e8690da6','2017-10-05 23:36:50',NULL,'\0','2017-10-05 23:36:50',NULL),(1610,'ritesh.nailwal','Add new proposal','2017-10-06','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@12b20454','2017-10-06 03:44:15',NULL,'\0','2017-10-06 03:44:15',NULL),(1611,'ritesh.nailwal','upload proposal document','2017-10-06','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@b91f74','2017-10-06 03:44:15',NULL,'\0','2017-10-06 03:44:15',NULL),(1612,'ritesh.nailwal','update proposal','2017-10-06','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@d3e0ab98','2017-10-06 03:44:22',NULL,'\0','2017-10-06 03:44:22',NULL),(1613,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:52:01',NULL,'\0','2017-10-06 03:52:01',NULL),(1614,'sumit.chaturvedi','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:52:01',NULL,'\0','2017-10-06 03:52:01',NULL),(1615,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:52:55',NULL,'\0','2017-10-06 03:52:55',NULL),(1616,'vikas.parashar','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:53:19',NULL,'\0','2017-10-06 03:53:19',NULL),(1617,'vikas.parashar','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:53:19',NULL,'\0','2017-10-06 03:53:19',NULL),(1618,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:53:59',NULL,'\0','2017-10-06 03:53:59',NULL),(1619,'sumit.chaturvedi','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:53:59',NULL,'\0','2017-10-06 03:53:59',NULL),(1620,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:54:09',NULL,'\0','2017-10-06 03:54:09',NULL),(1621,'paras.mishra','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:54:29',NULL,'\0','2017-10-06 03:54:29',NULL),(1622,'paras.mishra','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:54:29',NULL,'\0','2017-10-06 03:54:29',NULL),(1623,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:54:51',NULL,'\0','2017-10-06 03:54:51',NULL),(1624,'sumit.chaturvedi','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:54:51',NULL,'\0','2017-10-06 03:54:51',NULL),(1625,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:55:08',NULL,'\0','2017-10-06 03:55:08',NULL),(1626,'ritesh.nailwal','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:57:14',NULL,'\0','2017-10-06 03:57:14',NULL),(1627,'ritesh.nailwal','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145014','2017-10-06 03:57:14',NULL,'\0','2017-10-06 03:57:14',NULL),(1628,'ritesh.nailwal','Add new proposal','2017-10-06','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@7b2cd394','2017-10-06 03:58:22',NULL,'\0','2017-10-06 03:58:22',NULL),(1629,'ritesh.nailwal','upload proposal document','2017-10-06','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@1d87611a','2017-10-06 03:58:22',NULL,'\0','2017-10-06 03:58:22',NULL),(1630,'ritesh.nailwal','update proposal','2017-10-06','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@993a8201','2017-10-06 03:58:41',NULL,'\0','2017-10-06 03:58:41',NULL),(1631,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:00:59',NULL,'\0','2017-10-06 04:00:59',NULL),(1632,'sumit.chaturvedi','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:00:59',NULL,'\0','2017-10-06 04:00:59',NULL),(1633,'vikas.parashar','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:01:34',NULL,'\0','2017-10-06 04:01:34',NULL),(1634,'vikas.parashar','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:01:40',NULL,'\0','2017-10-06 04:01:40',NULL),(1635,'vikas.parashar','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:01:40',NULL,'\0','2017-10-06 04:01:40',NULL),(1636,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:02:12',NULL,'\0','2017-10-06 04:02:12',NULL),(1637,'sumit.chaturvedi','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:02:13',NULL,'\0','2017-10-06 04:02:13',NULL),(1638,'paras.mishra','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:03:45',NULL,'\0','2017-10-06 04:03:45',NULL),(1639,'paras.mishra','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:03:50',NULL,'\0','2017-10-06 04:03:50',NULL),(1640,'paras.mishra','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:03:50',NULL,'\0','2017-10-06 04:03:50',NULL),(1641,'sumit.chaturvedi','assign task','2017-10-06','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:07:50',NULL,'\0','2017-10-06 04:07:50',NULL),(1642,'sumit.chaturvedi','execute task','2017-10-06','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-06 04:07:50',NULL,'\0','2017-10-06 04:07:50',NULL),(1643,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@10e92b3','2017-10-07 03:02:46',NULL,'\0','2017-10-07 03:02:46',NULL),(1644,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@dd4234be','2017-10-07 04:06:51',NULL,'\0','2017-10-07 04:06:51',NULL),(1645,'ritesh.nailwal','Add new opportunity','2017-10-07','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@b71921f4','2017-10-07 06:35:11',NULL,'\0','2017-10-07 06:35:11',NULL),(1646,'ritesh.nailwal','Add new opportunity','2017-10-07','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@f47e2954','2017-10-07 06:37:04',NULL,'\0','2017-10-07 06:37:04',NULL),(1647,'ritesh.nailwal','Add new opportunity','2017-10-07','OpportunityController.addNewOpportunity(..)','com.oms.controller.OpportunityController','com.oms.model.Opportunity@90a11ef4','2017-10-07 07:01:41',NULL,'\0','2017-10-07 07:01:41',NULL),(1648,'ritesh.nailwal','upload opportunity document','2017-10-07','OpportunityController.uploadDocuments(..)','com.oms.controller.OpportunityController','com.oms.model.OpportunityDocuments@6057eefd','2017-10-07 07:11:57',NULL,'\0','2017-10-07 07:11:57',NULL),(1649,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-07 12:32:57',NULL,'\0','2017-10-07 12:32:57',NULL),(1650,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-07 12:33:03',NULL,'\0','2017-10-07 12:33:03',NULL),(1651,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-07 12:33:43',NULL,'\0','2017-10-07 12:33:43',NULL),(1652,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-07 12:34:33',NULL,'\0','2017-10-07 12:34:33',NULL),(1653,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1145095','2017-10-07 12:34:40',NULL,'\0','2017-10-07 12:34:40',NULL),(1654,'ritesh.nailwal','Add new proposal','2017-10-07','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@f6cba879','2017-10-07 12:35:55',NULL,'\0','2017-10-07 12:35:55',NULL),(1655,'ritesh.nailwal','upload proposal document','2017-10-07','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@627a2403','2017-10-07 12:35:55',NULL,'\0','2017-10-07 12:35:55',NULL),(1656,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@a01b3321','2017-10-07 12:36:04',NULL,'\0','2017-10-07 12:36:04',NULL),(1657,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@a271d0d8','2017-10-07 12:36:45',NULL,'\0','2017-10-07 12:36:45',NULL),(1658,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:37:36',NULL,'\0','2017-10-07 12:37:36',NULL),(1659,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:37:36',NULL,'\0','2017-10-07 12:37:36',NULL),(1660,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:38:19',NULL,'\0','2017-10-07 12:38:19',NULL),(1661,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:38:23',NULL,'\0','2017-10-07 12:38:23',NULL),(1662,'vikas.parashar','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:38:23',NULL,'\0','2017-10-07 12:38:23',NULL),(1663,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:38:50',NULL,'\0','2017-10-07 12:38:50',NULL),(1664,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 12:38:51',NULL,'\0','2017-10-07 12:38:51',NULL),(1665,'paras.mishra','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:08:04',NULL,'\0','2017-10-07 13:08:04',NULL),(1666,'paras.mishra','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:08:09',NULL,'\0','2017-10-07 13:08:09',NULL),(1667,'paras.mishra','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:08:10',NULL,'\0','2017-10-07 13:08:10',NULL),(1668,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:08:36',NULL,'\0','2017-10-07 13:08:36',NULL),(1669,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:08:37',NULL,'\0','2017-10-07 13:08:37',NULL),(1670,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:09:09',NULL,'\0','2017-10-07 13:09:09',NULL),(1671,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:09:33',NULL,'\0','2017-10-07 13:09:33',NULL),(1672,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:09:55',NULL,'\0','2017-10-07 13:09:55',NULL),(1673,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:14:03',NULL,'\0','2017-10-07 13:14:03',NULL),(1674,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 13:14:38',NULL,'\0','2017-10-07 13:14:38',NULL),(1675,'ritesh.nailwal','Add new proposal','2017-10-07','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@ba531ed3','2017-10-07 13:16:20',NULL,'\0','2017-10-07 13:16:20',NULL),(1676,'ritesh.nailwal','upload proposal document','2017-10-07','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@7a2a1c4a','2017-10-07 13:16:20',NULL,'\0','2017-10-07 13:16:20',NULL),(1677,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@104a0b3b','2017-10-07 13:16:31',NULL,'\0','2017-10-07 13:16:31',NULL),(1678,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@12224607','2017-10-07 13:17:04',NULL,'\0','2017-10-07 13:17:04',NULL),(1679,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:17:56',NULL,'\0','2017-10-07 13:17:56',NULL),(1680,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:18:05',NULL,'\0','2017-10-07 13:18:05',NULL),(1681,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:18:40',NULL,'\0','2017-10-07 13:18:40',NULL),(1682,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:18:44',NULL,'\0','2017-10-07 13:18:44',NULL),(1683,'vikas.parashar','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:18:44',NULL,'\0','2017-10-07 13:18:44',NULL),(1684,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:19:18',NULL,'\0','2017-10-07 13:19:18',NULL),(1685,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:19:18',NULL,'\0','2017-10-07 13:19:18',NULL),(1686,'paras.mishra','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 13:20:38',NULL,'\0','2017-10-07 13:20:38',NULL),(1687,'ritesh.nailwal','Add new proposal','2017-10-07','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@a41ac7bb','2017-10-07 13:47:20',NULL,'\0','2017-10-07 13:47:20',NULL),(1688,'ritesh.nailwal','upload proposal document','2017-10-07','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@5ca56d3c','2017-10-07 13:47:20',NULL,'\0','2017-10-07 13:47:20',NULL),(1689,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@9d5f3e56','2017-10-07 13:47:25',NULL,'\0','2017-10-07 13:47:25',NULL),(1690,'swati.khurana','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 13:47:58',NULL,'\0','2017-10-07 13:47:58',NULL),(1691,'swati.khurana','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 13:47:58',NULL,'\0','2017-10-07 13:47:58',NULL),(1692,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 13:48:30',NULL,'\0','2017-10-07 13:48:30',NULL),(1693,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 13:48:34',NULL,'\0','2017-10-07 13:48:34',NULL),(1694,'vikas.parashar','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 13:48:34',NULL,'\0','2017-10-07 13:48:34',NULL),(1695,'swati.khurana','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@c960a18a','2017-10-07 14:00:13',NULL,'\0','2017-10-07 14:00:13',NULL),(1696,'swati.khurana','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 14:00:38',NULL,'\0','2017-10-07 14:00:38',NULL),(1697,'swati.khurana','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1325005','2017-10-07 14:00:38',NULL,'\0','2017-10-07 14:00:38',NULL),(1698,'paras.mishra','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 14:00:55',NULL,'\0','2017-10-07 14:00:55',NULL),(1699,'paras.mishra','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 14:00:56',NULL,'\0','2017-10-07 14:00:56',NULL),(1700,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 14:01:16',NULL,'\0','2017-10-07 14:01:16',NULL),(1701,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322552','2017-10-07 14:01:17',NULL,'\0','2017-10-07 14:01:17',NULL),(1702,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:02:22',NULL,'\0','2017-10-07 14:02:22',NULL),(1703,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:02:22',NULL,'\0','2017-10-07 14:02:22',NULL),(1704,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:02:46',NULL,'\0','2017-10-07 14:02:46',NULL),(1705,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:02:46',NULL,'\0','2017-10-07 14:02:46',NULL),(1706,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:03:12',NULL,'\0','2017-10-07 14:03:12',NULL),(1707,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:03:12',NULL,'\0','2017-10-07 14:03:12',NULL),(1708,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:05:52',NULL,'\0','2017-10-07 14:05:52',NULL),(1709,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:06:12',NULL,'\0','2017-10-07 14:06:12',NULL),(1710,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:07:43',NULL,'\0','2017-10-07 14:07:43',NULL),(1711,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 14:07:43',NULL,'\0','2017-10-07 14:07:43',NULL),(1712,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 15:04:07',NULL,'\0','2017-10-07 15:04:07',NULL),(1713,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 15:04:11',NULL,'\0','2017-10-07 15:04:11',NULL),(1714,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 15:05:01',NULL,'\0','2017-10-07 15:05:01',NULL),(1715,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1227512','2017-10-07 15:06:06',NULL,'\0','2017-10-07 15:06:06',NULL),(1716,'ritesh.nailwal','Add new proposal','2017-10-07','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@70c691fd','2017-10-07 15:08:04',NULL,'\0','2017-10-07 15:08:04',NULL),(1717,'ritesh.nailwal','upload proposal document','2017-10-07','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@14822fcc','2017-10-07 15:08:04',NULL,'\0','2017-10-07 15:08:04',NULL),(1718,'sumit.chaturvedi','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@b9d68e35','2017-10-07 15:09:31',NULL,'\0','2017-10-07 15:09:31',NULL),(1719,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:09:57',NULL,'\0','2017-10-07 15:09:57',NULL),(1720,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:10:05',NULL,'\0','2017-10-07 15:10:05',NULL),(1721,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:12:56',NULL,'\0','2017-10-07 15:12:56',NULL),(1722,'vikas.parashar','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:13:02',NULL,'\0','2017-10-07 15:13:02',NULL),(1723,'vikas.parashar','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:13:12',NULL,'\0','2017-10-07 15:13:12',NULL),(1724,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:13:45',NULL,'\0','2017-10-07 15:13:45',NULL),(1725,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:14:01',NULL,'\0','2017-10-07 15:14:01',NULL),(1726,'paras.mishra','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:15:58',NULL,'\0','2017-10-07 15:15:58',NULL),(1727,'paras.mishra','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:16:05',NULL,'\0','2017-10-07 15:16:05',NULL),(1728,'paras.mishra','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:16:19',NULL,'\0','2017-10-07 15:16:19',NULL),(1729,'sumit.chaturvedi','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:17:20',NULL,'\0','2017-10-07 15:17:20',NULL),(1730,'sumit.chaturvedi','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:17:29',NULL,'\0','2017-10-07 15:17:29',NULL),(1731,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:18:15',NULL,'\0','2017-10-07 15:18:15',NULL),(1732,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:18:27',NULL,'\0','2017-10-07 15:18:27',NULL),(1733,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:18:43',NULL,'\0','2017-10-07 15:18:43',NULL),(1734,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:20:09',NULL,'\0','2017-10-07 15:20:09',NULL),(1735,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:20:15',NULL,'\0','2017-10-07 15:20:15',NULL),(1736,'ritesh.nailwal','Add new proposal','2017-10-07','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@bf376ef3','2017-10-07 15:23:00',NULL,'\0','2017-10-07 15:23:00',NULL),(1737,'ritesh.nailwal','upload proposal document','2017-10-07','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@1921789d','2017-10-07 15:23:00',NULL,'\0','2017-10-07 15:23:00',NULL),(1738,'ritesh.nailwal','update proposal','2017-10-07','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@6a7d6e76','2017-10-07 15:23:12',NULL,'\0','2017-10-07 15:23:12',NULL),(1739,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:23:47',NULL,'\0','2017-10-07 15:23:47',NULL),(1740,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:23:52',NULL,'\0','2017-10-07 15:23:52',NULL),(1741,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:25:28',NULL,'\0','2017-10-07 15:25:28',NULL),(1742,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:29:40',NULL,'\0','2017-10-07 15:29:40',NULL),(1743,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:35:01',NULL,'\0','2017-10-07 15:35:01',NULL),(1744,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:35:09',NULL,'\0','2017-10-07 15:35:09',NULL),(1745,'ritesh.nailwal','assign task','2017-10-07','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:38:34',NULL,'\0','2017-10-07 15:38:34',NULL),(1746,'ritesh.nailwal','execute task','2017-10-07','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1322594','2017-10-07 15:38:42',NULL,'\0','2017-10-07 15:38:42',NULL),(1747,'ritesh.nailwal','Add new proposal','2017-10-08','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@5534daee','2017-10-08 20:17:14',NULL,'\0','2017-10-08 20:17:14',NULL),(1748,'ritesh.nailwal','upload proposal document','2017-10-08','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@51a8fca3','2017-10-08 20:17:14',NULL,'\0','2017-10-08 20:17:14',NULL),(1749,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@cd7135bf','2017-10-08 20:17:23',NULL,'\0','2017-10-08 20:17:23',NULL),(1750,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@d3956850','2017-10-08 20:19:11',NULL,'\0','2017-10-08 20:19:11',NULL),(1751,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@a1fb1ab4','2017-10-08 21:19:14',NULL,'\0','2017-10-08 21:19:14',NULL),(1752,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@be11aeb2','2017-10-08 21:27:24',NULL,'\0','2017-10-08 21:27:24',NULL),(1753,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@c90c241f','2017-10-08 21:35:48',NULL,'\0','2017-10-08 21:35:48',NULL),(1754,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@e55f74cb','2017-10-08 21:39:17',NULL,'\0','2017-10-08 21:39:17',NULL),(1755,'ritesh.nailwal','update proposal','2017-10-08','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@f0b6fc43','2017-10-08 21:43:05',NULL,'\0','2017-10-08 21:43:05',NULL),(1756,'ritesh.nailwal','Add new proposal','2017-10-09','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@8fee1651','2017-10-09 12:27:14',NULL,'\0','2017-10-09 12:27:14',NULL),(1757,'ritesh.nailwal','upload proposal document','2017-10-09','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@43a2e99c','2017-10-09 12:27:15',NULL,'\0','2017-10-09 12:27:15',NULL),(1758,'ritesh.nailwal','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@86023d91','2017-10-09 12:27:53',NULL,'\0','2017-10-09 12:27:53',NULL),(1759,'ritesh.nailwal','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@886dd71f','2017-10-09 12:28:35',NULL,'\0','2017-10-09 12:28:35',NULL),(1760,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@b665659b','2017-10-10 00:30:17',NULL,'\0','2017-10-10 00:30:17',NULL),(1761,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@13fc5fc8','2017-10-10 00:30:18',NULL,'\0','2017-10-10 00:30:18',NULL),(1762,'sumit.chaturvedi','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@74cb6826','2017-10-10 00:31:31',NULL,'\0','2017-10-10 00:31:31',NULL),(1763,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:32:08',NULL,'\0','2017-10-10 00:32:08',NULL),(1764,'sumit.chaturvedi','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:32:08',NULL,'\0','2017-10-10 00:32:08',NULL),(1765,'vikas.parashar','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:32:48',NULL,'\0','2017-10-10 00:32:48',NULL),(1766,'vikas.parashar','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:32:52',NULL,'\0','2017-10-10 00:32:52',NULL),(1767,'vikas.parashar','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:32:52',NULL,'\0','2017-10-10 00:32:52',NULL),(1768,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:33:23',NULL,'\0','2017-10-10 00:33:23',NULL),(1769,'sumit.chaturvedi','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:33:23',NULL,'\0','2017-10-10 00:33:23',NULL),(1770,'paras.mishra','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:34:04',NULL,'\0','2017-10-10 00:34:04',NULL),(1771,'paras.mishra','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:34:08',NULL,'\0','2017-10-10 00:34:08',NULL),(1772,'paras.mishra','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:34:08',NULL,'\0','2017-10-10 00:34:08',NULL),(1773,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:34:52',NULL,'\0','2017-10-10 00:34:52',NULL),(1774,'sumit.chaturvedi','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:34:52',NULL,'\0','2017-10-10 00:34:52',NULL),(1775,'ritesh.nailwal','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:35:19',NULL,'\0','2017-10-10 00:35:19',NULL),(1776,'ritesh.nailwal','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:35:35',NULL,'\0','2017-10-10 00:35:35',NULL),(1777,'ritesh.nailwal','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','5','2017-10-10 00:35:35',NULL,'\0','2017-10-10 00:35:35',NULL),(1778,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@51b3f23','2017-10-10 00:58:34',NULL,'\0','2017-10-10 00:58:34',NULL),(1779,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@424df255','2017-10-10 00:58:34',NULL,'\0','2017-10-10 00:58:34',NULL),(1780,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@e4d918fe','2017-10-10 00:58:40',NULL,'\0','2017-10-10 00:58:40',NULL),(1781,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@e965fb4e','2017-10-10 01:00:00',NULL,'\0','2017-10-10 01:00:00',NULL),(1782,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@47c971c','2017-10-10 01:07:52',NULL,'\0','2017-10-10 01:07:52',NULL),(1783,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@5b848f5','2017-10-10 01:08:13',NULL,'\0','2017-10-10 01:08:13',NULL),(1784,'ritesh.nailwal','Add new proposal','2017-10-09','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@5e41e38b','2017-10-09 15:43:58',NULL,'\0','2017-10-09 15:43:58',NULL),(1785,'ritesh.nailwal','upload proposal document','2017-10-09','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@1b807991','2017-10-09 15:43:59',NULL,'\0','2017-10-09 15:43:59',NULL),(1786,'ritesh.nailwal','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@2b985341','2017-10-09 15:44:39',NULL,'\0','2017-10-09 15:44:39',NULL),(1787,'ritesh.nailwal','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@2fb439b8','2017-10-09 15:45:50',NULL,'\0','2017-10-09 15:45:50',NULL),(1788,'sumit.chaturvedi','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@3328c575','2017-10-09 15:46:50',NULL,'\0','2017-10-09 15:46:50',NULL),(1789,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:47:12',NULL,'\0','2017-10-09 15:47:12',NULL),(1790,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:47:12',NULL,'\0','2017-10-09 15:47:12',NULL),(1791,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:47:48',NULL,'\0','2017-10-09 15:47:48',NULL),(1792,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:47:54',NULL,'\0','2017-10-09 15:47:54',NULL),(1793,'vikas.parashar','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:47:55',NULL,'\0','2017-10-09 15:47:55',NULL),(1794,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:48:27',NULL,'\0','2017-10-09 15:48:27',NULL),(1795,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:48:27',NULL,'\0','2017-10-09 15:48:27',NULL),(1796,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:49:12',NULL,'\0','2017-10-09 15:49:12',NULL),(1797,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:49:18',NULL,'\0','2017-10-09 15:49:18',NULL),(1798,'paras.mishra','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:49:19',NULL,'\0','2017-10-09 15:49:19',NULL),(1799,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:50:02',NULL,'\0','2017-10-09 15:50:02',NULL),(1800,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:50:02',NULL,'\0','2017-10-09 15:50:02',NULL),(1801,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:50:34',NULL,'\0','2017-10-09 15:50:34',NULL),(1802,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:51:07',NULL,'\0','2017-10-09 15:51:07',NULL),(1803,'ritesh.nailwal','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','2505','2017-10-09 15:51:07',NULL,'\0','2017-10-09 15:51:07',NULL),(1804,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@c1a5ab56','2017-10-10 02:30:01',NULL,'\0','2017-10-10 02:30:01',NULL),(1805,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@1678041d','2017-10-10 02:30:01',NULL,'\0','2017-10-10 02:30:01',NULL),(1806,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@29f69e92','2017-10-10 02:30:08',NULL,'\0','2017-10-10 02:30:08',NULL),(1807,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@2b8466c9','2017-10-10 02:30:35',NULL,'\0','2017-10-10 02:30:35',NULL),(1808,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@2ea21788','2017-10-10 02:31:30',NULL,'\0','2017-10-10 02:31:30',NULL),(1809,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@c9d2e453','2017-10-10 02:35:27',NULL,'\0','2017-10-10 02:35:27',NULL),(1810,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@42849404','2017-10-10 02:35:27',NULL,'\0','2017-10-10 02:35:27',NULL),(1811,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@ab4c7339','2017-10-10 02:37:30',NULL,'\0','2017-10-10 02:37:30',NULL),(1812,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@4c667684','2017-10-10 02:40:09',NULL,'\0','2017-10-10 02:40:09',NULL),(1813,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@bbd0b656','2017-10-10 02:42:19',NULL,'\0','2017-10-10 02:42:19',NULL),(1814,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@aafb865d','2017-10-10 03:07:40',NULL,'\0','2017-10-10 03:07:40',NULL),(1815,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@40d28269','2017-10-10 03:10:26',NULL,'\0','2017-10-10 03:10:26',NULL),(1816,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@7d560451','2017-10-10 03:10:26',NULL,'\0','2017-10-10 03:10:26',NULL),(1817,'swati.khurana','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@7cb3f414','2017-10-10 03:11:56',NULL,'\0','2017-10-10 03:11:56',NULL),(1818,'swati.khurana','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@7f93a18e','2017-10-10 03:12:46',NULL,'\0','2017-10-10 03:12:46',NULL),(1819,'swati.khurana','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@82b6e88a','2017-10-10 03:13:41',NULL,'\0','2017-10-10 03:13:41',NULL),(1820,'swati.khurana','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@4eadccf3','2017-10-10 03:13:41',NULL,'\0','2017-10-10 03:13:41',NULL),(1821,'swati.khurana','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@85378571','2017-10-10 03:14:24',NULL,'\0','2017-10-10 03:14:24',NULL),(1822,'swati.khurana','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@a24a01ca','2017-10-10 03:22:51',NULL,'\0','2017-10-10 03:22:51',NULL),(1823,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:24:24',NULL,'\0','2017-10-10 03:24:24',NULL),(1824,'swati.khurana','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:24:24',NULL,'\0','2017-10-10 03:24:24',NULL),(1825,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:25:53',NULL,'\0','2017-10-10 03:25:53',NULL),(1826,'vikas.parashar','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@b1594f34','2017-10-10 03:27:14',NULL,'\0','2017-10-10 03:27:14',NULL),(1827,'vikas.parashar','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@b6ca1c7b','2017-10-10 03:28:50',NULL,'\0','2017-10-10 03:28:50',NULL),(1828,'vikas.parashar','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@bb7f46c6','2017-10-10 03:30:12',NULL,'\0','2017-10-10 03:30:12',NULL),(1829,'vikas.parashar','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@4b709bf0','2017-10-10 03:30:12',NULL,'\0','2017-10-10 03:30:12',NULL),(1830,'vikas.parashar','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:30:23',NULL,'\0','2017-10-10 03:30:23',NULL),(1831,'vikas.parashar','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:30:23',NULL,'\0','2017-10-10 03:30:23',NULL),(1832,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:31:09',NULL,'\0','2017-10-10 03:31:09',NULL),(1833,'swati.khurana','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:31:09',NULL,'\0','2017-10-10 03:31:09',NULL),(1834,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:31:29',NULL,'\0','2017-10-10 03:31:29',NULL),(1835,'paras.mishra','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:32:02',NULL,'\0','2017-10-10 03:32:02',NULL),(1836,'paras.mishra','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:32:02',NULL,'\0','2017-10-10 03:32:02',NULL),(1837,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:32:34',NULL,'\0','2017-10-10 03:32:34',NULL),(1838,'swati.khurana','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:32:34',NULL,'\0','2017-10-10 03:32:34',NULL),(1839,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:32:46',NULL,'\0','2017-10-10 03:32:46',NULL),(1840,'ritesh.nailwal','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:34:38',NULL,'\0','2017-10-10 03:34:38',NULL),(1841,'ritesh.nailwal','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','99','2017-10-10 03:34:38',NULL,'\0','2017-10-10 03:34:38',NULL),(1842,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@593d1a94','2017-10-10 03:42:39',NULL,'\0','2017-10-10 03:42:39',NULL),(1843,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@42e59e9f','2017-10-10 03:42:40',NULL,'\0','2017-10-10 03:42:40',NULL),(1844,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@f1ebcf3b','2017-10-10 03:42:54',NULL,'\0','2017-10-10 03:42:54',NULL),(1845,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@1e98c9b5','2017-10-10 03:42:54',NULL,'\0','2017-10-10 03:42:54',NULL),(1846,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@a59baedb','2017-10-10 03:44:31',NULL,'\0','2017-10-10 03:44:31',NULL),(1847,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@8e3248d','2017-10-10 03:58:29',NULL,'\0','2017-10-10 03:58:29',NULL),(1848,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@bd0138f','2017-10-10 03:59:20',NULL,'\0','2017-10-10 03:59:20',NULL),(1849,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@3a958c5f','2017-10-10 03:59:20',NULL,'\0','2017-10-10 03:59:20',NULL),(1850,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@59c3d613','2017-10-10 04:02:09',NULL,'\0','2017-10-10 04:02:09',NULL),(1851,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@47764fd3','2017-10-10 04:02:09',NULL,'\0','2017-10-10 04:02:09',NULL),(1852,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@7e7453f5','2017-10-10 04:02:56',NULL,'\0','2017-10-10 04:02:56',NULL),(1853,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@813178fc','2017-10-10 04:03:44',NULL,'\0','2017-10-10 04:03:44',NULL),(1854,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@837be373','2017-10-10 04:04:24',NULL,'\0','2017-10-10 04:04:24',NULL),(1855,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@85d56870','2017-10-10 04:05:05',NULL,'\0','2017-10-10 04:05:05',NULL),(1856,'ritesh.nailwal','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@87ce03dc','2017-10-10 04:05:39',NULL,'\0','2017-10-10 04:05:39',NULL),(1857,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@32070074','2017-10-10 04:05:39',NULL,'\0','2017-10-10 04:05:39',NULL),(1858,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:06:11',NULL,'\0','2017-10-10 04:06:11',NULL),(1859,'swati.khurana','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:06:11',NULL,'\0','2017-10-10 04:06:11',NULL),(1860,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:07:09',NULL,'\0','2017-10-10 04:07:09',NULL),(1861,'vikas.parashar','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:07:50',NULL,'\0','2017-10-10 04:07:50',NULL),(1862,'vikas.parashar','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:07:50',NULL,'\0','2017-10-10 04:07:50',NULL),(1863,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:08:45',NULL,'\0','2017-10-10 04:08:45',NULL),(1864,'swati.khurana','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:08:45',NULL,'\0','2017-10-10 04:08:45',NULL),(1865,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:09:04',NULL,'\0','2017-10-10 04:09:04',NULL),(1866,'paras.mishra','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:09:26',NULL,'\0','2017-10-10 04:09:26',NULL),(1867,'paras.mishra','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:09:27',NULL,'\0','2017-10-10 04:09:27',NULL),(1868,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:10:05',NULL,'\0','2017-10-10 04:10:05',NULL),(1869,'swati.khurana','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:10:05',NULL,'\0','2017-10-10 04:10:05',NULL),(1870,'swati.khurana','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:10:19',NULL,'\0','2017-10-10 04:10:19',NULL),(1871,'ritesh.nailwal','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:11:02',NULL,'\0','2017-10-10 04:11:02',NULL),(1872,'ritesh.nailwal','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','10001','2017-10-10 04:11:02',NULL,'\0','2017-10-10 04:11:02',NULL),(1873,'sumit.chaturvedi','Add new proposal','2017-10-09','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@ce4f06bf','2017-10-09 19:06:58',NULL,'\0','2017-10-09 19:06:58',NULL),(1874,'sumit.chaturvedi','upload proposal document','2017-10-09','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@407aabac','2017-10-09 19:06:58',NULL,'\0','2017-10-09 19:06:58',NULL),(1875,'sumit.chaturvedi','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@9a844ec1','2017-10-09 19:08:20',NULL,'\0','2017-10-09 19:08:20',NULL),(1876,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:08:41',NULL,'\0','2017-10-09 19:08:41',NULL),(1877,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:08:42',NULL,'\0','2017-10-09 19:08:42',NULL),(1878,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:09:18',NULL,'\0','2017-10-09 19:09:18',NULL),(1879,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:09:23',NULL,'\0','2017-10-09 19:09:23',NULL),(1880,'vikas.parashar','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:09:24',NULL,'\0','2017-10-09 19:09:24',NULL),(1881,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:09:54',NULL,'\0','2017-10-09 19:09:54',NULL),(1882,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:09:55',NULL,'\0','2017-10-09 19:09:55',NULL),(1883,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:10:34',NULL,'\0','2017-10-09 19:10:34',NULL),(1884,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:10:39',NULL,'\0','2017-10-09 19:10:39',NULL),(1885,'paras.mishra','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:10:39',NULL,'\0','2017-10-09 19:10:39',NULL),(1886,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:11:15',NULL,'\0','2017-10-09 19:11:15',NULL),(1887,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:11:15',NULL,'\0','2017-10-09 19:11:15',NULL),(1888,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:12:17',NULL,'\0','2017-10-09 19:12:17',NULL),(1889,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:12:22',NULL,'\0','2017-10-09 19:12:22',NULL),(1890,'ritesh.nailwal','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1435001','2017-10-09 19:12:23',NULL,'\0','2017-10-09 19:12:23',NULL),(1891,'ritesh.nailwal','Add new proposal','2017-10-09','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@9d8f2606','2017-10-09 20:33:48',NULL,'\0','2017-10-09 20:33:48',NULL),(1892,'ritesh.nailwal','upload proposal document','2017-10-09','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@2e0f3fa0','2017-10-09 20:33:48',NULL,'\0','2017-10-09 20:33:48',NULL),(1893,'sumit.chaturvedi','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@703ffba6','2017-10-09 20:35:32',NULL,'\0','2017-10-09 20:35:32',NULL),(1894,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:35:55',NULL,'\0','2017-10-09 20:35:55',NULL),(1895,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:35:56',NULL,'\0','2017-10-09 20:35:56',NULL),(1896,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:36:32',NULL,'\0','2017-10-09 20:36:32',NULL),(1897,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:36:37',NULL,'\0','2017-10-09 20:36:37',NULL),(1898,'vikas.parashar','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:36:38',NULL,'\0','2017-10-09 20:36:38',NULL),(1899,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:37:12',NULL,'\0','2017-10-09 20:37:12',NULL),(1900,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:37:13',NULL,'\0','2017-10-09 20:37:13',NULL),(1901,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:37:59',NULL,'\0','2017-10-09 20:37:59',NULL),(1902,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:38:06',NULL,'\0','2017-10-09 20:38:06',NULL),(1903,'paras.mishra','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:38:07',NULL,'\0','2017-10-09 20:38:07',NULL),(1904,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:38:48',NULL,'\0','2017-10-09 20:38:48',NULL),(1905,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:38:49',NULL,'\0','2017-10-09 20:38:49',NULL),(1906,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:39:29',NULL,'\0','2017-10-09 20:39:29',NULL),(1907,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:39:35',NULL,'\0','2017-10-09 20:39:35',NULL),(1908,'ritesh.nailwal','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1437501','2017-10-09 20:39:37',NULL,'\0','2017-10-09 20:39:37',NULL),(1909,'ritesh.nailwal','Add new proposal','2017-10-09','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@e0813d41','2017-10-09 21:07:26',NULL,'\0','2017-10-09 21:07:26',NULL),(1910,'ritesh.nailwal','upload proposal document','2017-10-09','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@265562c','2017-10-09 21:07:27',NULL,'\0','2017-10-09 21:07:27',NULL),(1911,'sumit.chaturvedi','update proposal','2017-10-09','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@48b4adbd','2017-10-09 21:09:29',NULL,'\0','2017-10-09 21:09:29',NULL),(1912,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:09:49',NULL,'\0','2017-10-09 21:09:49',NULL),(1913,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:09:50',NULL,'\0','2017-10-09 21:09:50',NULL),(1914,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:10:36',NULL,'\0','2017-10-09 21:10:36',NULL),(1915,'vikas.parashar','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:10:42',NULL,'\0','2017-10-09 21:10:42',NULL),(1916,'vikas.parashar','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:10:43',NULL,'\0','2017-10-09 21:10:43',NULL),(1917,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:11:15',NULL,'\0','2017-10-09 21:11:15',NULL),(1918,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:11:15',NULL,'\0','2017-10-09 21:11:15',NULL),(1919,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:12:01',NULL,'\0','2017-10-09 21:12:01',NULL),(1920,'paras.mishra','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:12:07',NULL,'\0','2017-10-09 21:12:07',NULL),(1921,'paras.mishra','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:12:08',NULL,'\0','2017-10-09 21:12:08',NULL),(1922,'sumit.chaturvedi','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:12:41',NULL,'\0','2017-10-09 21:12:41',NULL),(1923,'sumit.chaturvedi','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:12:42',NULL,'\0','2017-10-09 21:12:42',NULL),(1924,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:13:42',NULL,'\0','2017-10-09 21:13:42',NULL),(1925,'ritesh.nailwal','assign task','2017-10-09','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:13:48',NULL,'\0','2017-10-09 21:13:48',NULL),(1926,'ritesh.nailwal','execute task','2017-10-09','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1440001','2017-10-09 21:13:49',NULL,'\0','2017-10-09 21:13:49',NULL),(1927,'ritesh.nailwal','Add new proposal','2017-10-10','ProposalController.addNewProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@cad16cba','2017-10-10 09:49:19',NULL,'\0','2017-10-10 09:49:19',NULL),(1928,'ritesh.nailwal','upload proposal document','2017-10-10','ProposalController.uploadDocuments(..)','com.oms.controller.ProposalController','com.oms.model.ProposalDocuments@6fc7f60d','2017-10-10 09:49:19',NULL,'\0','2017-10-10 09:49:19',NULL),(1929,'sumit.chaturvedi','update proposal','2017-10-10','ProposalController.updateProposal(..)','com.oms.controller.ProposalController','com.oms.model.Proposal@90dce14b','2017-10-10 09:50:48',NULL,'\0','2017-10-10 09:50:48',NULL),(1930,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:51:02',NULL,'\0','2017-10-10 09:51:02',NULL),(1931,'sumit.chaturvedi','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:51:03',NULL,'\0','2017-10-10 09:51:03',NULL),(1932,'vikas.parashar','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:54:26',NULL,'\0','2017-10-10 09:54:26',NULL),(1933,'vikas.parashar','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:54:32',NULL,'\0','2017-10-10 09:54:32',NULL),(1934,'vikas.parashar','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:54:33',NULL,'\0','2017-10-10 09:54:33',NULL),(1935,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:55:18',NULL,'\0','2017-10-10 09:55:18',NULL),(1936,'sumit.chaturvedi','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:55:19',NULL,'\0','2017-10-10 09:55:19',NULL),(1937,'paras.mishra','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:56:09',NULL,'\0','2017-10-10 09:56:09',NULL),(1938,'paras.mishra','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:56:14',NULL,'\0','2017-10-10 09:56:14',NULL),(1939,'paras.mishra','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:56:15',NULL,'\0','2017-10-10 09:56:15',NULL),(1940,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:56:55',NULL,'\0','2017-10-10 09:56:55',NULL),(1941,'sumit.chaturvedi','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:57:00',NULL,'\0','2017-10-10 09:57:00',NULL),(1942,'sumit.chaturvedi','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:57:01',NULL,'\0','2017-10-10 09:57:01',NULL),(1943,'ritesh.nailwal','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:57:34',NULL,'\0','2017-10-10 09:57:34',NULL),(1944,'ritesh.nailwal','assign task','2017-10-10','OMSApprovalActivityWorkFlow.assignTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:57:41',NULL,'\0','2017-10-10 09:57:41',NULL),(1945,'ritesh.nailwal','execute task','2017-10-10','OMSApprovalActivityWorkFlow.executeTask(..)','com.oms.controller.OMSApprovalActivityWorkFlow','1442501','2017-10-10 09:57:41',NULL,'\0','2017-10-10 09:57:41',NULL);
/*!40000 ALTER TABLE `audit_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billing_source`
--

DROP TABLE IF EXISTS `billing_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_source` (
  `billing_source_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT b'1',
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`billing_source_ID`),
  KEY `billing_src_customer_idx` (`CUSTOMER_ID`),
  CONSTRAINT `billing_src_customer` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billing_source`
--

LOCK TABLES `billing_source` WRITE;
/*!40000 ALTER TABLE `billing_source` DISABLE KEYS */;
INSERT INTO `billing_source` VALUES (1,'Contracted','',NULL,NULL,'\0',NULL,NULL,NULL),(2,'First Party Volume','',NULL,NULL,'\0',NULL,NULL,NULL),(3,'Third Party Volume','',NULL,NULL,'\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `billing_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `COMPANY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE` enum('Agency','Advertiser') NOT NULL DEFAULT 'Agency',
  `NAME` varchar(45) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `STATUS_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `address` varchar(999) DEFAULT NULL,
  `vpzid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`COMPANY_ID`),
  KEY `CUSTOMER_ID_idx` (`CUSTOMER_ID`),
  KEY `COMOANY_STATUS_idx` (`STATUS_ID`),
  CONSTRAINT `company_status_forkey` FOREIGN KEY (`STATUS_ID`) REFERENCES `company_status` (`COMPANY_STATUS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (106,'Advertiser','Pepsi',3,2,'2017-06-12 10:43:41',14,'\0','2017-06-12 10:43:41',14,'Plot Number B-5 , Sector -62 , Noida','00d4bd2d-484d-4b0a-b96e-44cd1b37ea64'),(107,'Advertiser','Ford',3,2,'2017-06-12 10:44:14',14,'\0','2017-08-09 23:49:16',17,'Sector-8 , Noida , UP -20130','0491cd99-b9ee-44f5-8043-d45fca6bc954'),(108,'Agency','MadHouse',3,4,'2017-06-12 10:44:50',14,'\0','2017-06-12 10:44:50',14,'Wille Parle Mumbai , India','230168f1-aafd-4af2-b5cc-7c27e9c5f229'),(109,'Agency','MediaHouse',3,2,'2017-06-12 10:45:27',14,'\0','2017-06-12 10:45:27',14,'Nehru Place Delhi -110020','24c4c353-39d2-452b-ad10-439aa9642565'),(115,'Agency','Justju',3,2,'2017-06-28 16:25:25',4,'','2017-06-28 16:26:39',4,'Nehru Palace',NULL),(116,'Advertiser','Summit India',3,2,'2017-06-30 15:40:29',4,'\0','2017-08-10 02:16:14',17,'New',NULL),(117,'Advertiser','test',3,5,'2017-08-09 23:48:03',17,'','2017-08-09 23:48:09',17,'sfsf',NULL),(118,'Agency','test company ',3,2,'2017-08-10 02:13:58',17,'','2017-08-10 02:15:46',17,'test',NULL),(119,'Agency','new one',3,4,'2017-08-10 02:15:14',17,'','2017-08-10 02:15:14',17,'new one',NULL),(120,'Agency','Puja',3,2,'2017-08-11 17:01:58',17,'','2017-08-11 17:01:58',17,NULL,NULL),(121,'Agency','Pepsi',3,2,'2017-08-14 14:13:11',17,'','2017-08-14 14:13:20',17,NULL,NULL),(122,'Agency','pqr',3,2,'2017-08-14 14:15:05',17,'\0','2017-08-14 14:15:41',17,NULL,NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_status`
--

DROP TABLE IF EXISTS `company_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_status` (
  `COMPANY_STATUS_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`COMPANY_STATUS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_status`
--

LOCK TABLES `company_status` WRITE;
/*!40000 ALTER TABLE `company_status` DISABLE KEYS */;
INSERT INTO `company_status` VALUES (1,'On Hold','',NULL,NULL,'\0',NULL,NULL),(2,'Active','',NULL,NULL,'\0',NULL,NULL),(3,'Regular Status1','',NULL,NULL,'\0',NULL,NULL),(4,'Credit Stop','',NULL,NULL,'\0',NULL,NULL),(5,'Inactive','',NULL,NULL,'\0',NULL,NULL),(6,'Regular Status','',NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `company_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `CONTACT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `MOBILE` varchar(45) DEFAULT NULL,
  `ADDRESS` varchar(45) DEFAULT NULL,
  `company_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`CONTACT_ID`),
  KEY `company_ID_idx` (`company_ID`),
  CONSTRAINT `company_ID` FOREIGN KEY (`company_ID`) REFERENCES `company` (`COMPANY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (54,'Kapil Kaushik','kapil.kumar@tavant.com','01204030300','9873822199','Noida',107,'2017-06-12 10:49:26',14,'\0','2017-06-12 10:49:26',14),(55,'Neeraj Mittal','neeraj.mittal@tavant.com',NULL,'2398249739','Noida',108,'2017-06-12 10:49:57',14,'\0','2017-06-12 10:49:57',14),(56,'Vishal Jindal','vishal.jindal@tavant.com',NULL,'4590405904','Noida Sector 50',106,'2017-06-12 10:50:32',14,'\0','2017-07-11 11:37:11',418),(65,'dsad','da@c.com',NULL,NULL,NULL,107,'2017-07-04 11:20:19',4,'','2017-07-04 11:20:19',4),(66,'test','test@gmail.com',NULL,NULL,NULL,108,'2017-08-09 23:50:07',17,'','2017-08-09 23:50:25',17),(67,'test','test@gmail.com',NULL,NULL,NULL,107,'2017-08-10 00:07:37',17,'','2017-08-10 02:39:00',17),(68,'test contact','contact@tavant.com',NULL,NULL,NULL,108,'2017-08-10 02:38:11',17,'','2017-08-10 02:38:29',17);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creative`
--

DROP TABLE IF EXISTS `creative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative` (
  `CREATIVE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `HEIGHT_1` double DEFAULT NULL,
  `HEIGHT_2` double DEFAULT NULL,
  `WIDTH_1` double DEFAULT NULL,
  `WIDTH_2` double DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`CREATIVE_ID`),
  KEY `creative_customer_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `creative_customer_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=381 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creative`
--

LOCK TABLES `creative` WRITE;
/*!40000 ALTER TABLE `creative` DISABLE KEYS */;
INSERT INTO `creative` VALUES (376,'Leaderboard',' Leaderboard ad, which was introduced in 2003, has a standard size of 728 pixels wide by 90 pixels tall',90,728,728,90,'2017-09-08 16:40:08',14,'\0','2017-09-08 16:40:08',14,NULL),(377,'Big Ad',' A large outdoor advertising structure, typically found in high-traffic',500,NULL,1000,NULL,'2017-09-08 16:43:20',14,'\0','2017-09-08 16:43:20',14,NULL),(378,'Full Banner','To display in full page length and width',60,NULL,468,NULL,'2017-09-08 16:45:50',14,'\0','2017-09-08 16:45:50',14,NULL),(379,'Sky Scraper','A skyscraper ad is a tall and narrow banner advertisement usually placed to the right of content on a Web page. Standard dimensions for a skyscraper ad are 160 X 600 pixels. Like another popular type of Internet ad, the leaderboard, the skyscraper offers an advertiser a large space for a message.',600,NULL,120,NULL,'2017-09-08 16:47:17',14,'\0','2017-09-08 16:47:17',14,NULL),(380,'Square POP UP','Pop-up ads or pop-ups are forms of online advertising on the World Wide Web. Pop-ups are generally new web browser windows to display advertisements.',240,NULL,400,NULL,'2017-09-08 16:49:40',14,'\0','2017-09-08 16:49:40',14,NULL);
/*!40000 ALTER TABLE `creative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creative_asset`
--

DROP TABLE IF EXISTS `creative_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_asset` (
  `creative_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL DEFAULT 'standard',
  `insertionpoint` varchar(45) NOT NULL DEFAULT 'preroll',
  `click_destination_uri` varchar(100) DEFAULT NULL,
  `vast_uri` varchar(45) DEFAULT NULL,
  `vpaid_strict` bit(1) DEFAULT NULL,
  `vpaid_countdown` bit(1) DEFAULT b'0',
  `assetid` varchar(45) CHARACTER SET big5 NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `created` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`creative_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creative_asset`
--

LOCK TABLES `creative_asset` WRITE;
/*!40000 ALTER TABLE `creative_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `creative_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `CUSTOMER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) NOT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '0',
  `ADDRESS` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2,'hotstar','ritesh.nailwal@tavant.cim','9818871522',100,NULL,NULL,NULL,'\0',NULL,NULL),(3,'hotstar','ritesh.nailwal@tavant.cim','9818871522',100,NULL,NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_impressions`
--

DROP TABLE IF EXISTS `delivery_impressions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delivery_impressions` (
  `impressionid` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`impressionid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_impressions`
--

LOCK TABLES `delivery_impressions` WRITE;
/*!40000 ALTER TABLE `delivery_impressions` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_impressions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `display_creative`
--

DROP TABLE IF EXISTS `display_creative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `display_creative` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `display_creative`
--

LOCK TABLES `display_creative` WRITE;
/*!40000 ALTER TABLE `display_creative` DISABLE KEYS */;
/*!40000 ALTER TABLE `display_creative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `high_level_permission`
--

DROP TABLE IF EXISTS `high_level_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `high_level_permission` (
  `PERMISSION_COMPONENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PERMISSION_NAME` varchar(45) DEFAULT NULL,
  `PERMISSION_DESCRIPTION` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PERMISSION_COMPONENT_ID`),
  KEY `customer_per_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `customer_per_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `high_level_permission`
--

LOCK TABLES `high_level_permission` WRITE;
/*!40000 ALTER TABLE `high_level_permission` DISABLE KEYS */;
INSERT INTO `high_level_permission` VALUES (1,'Admin','Admin',NULL,NULL,'\0',NULL,NULL,NULL),(2,'Sales','Sales',NULL,NULL,'\0',NULL,NULL,NULL),(3,'Financial','Financial',NULL,NULL,'\0',NULL,NULL,NULL),(4,'Reports','Reports',NULL,NULL,'\0',NULL,NULL,NULL),(5,'Delivery','Reports',NULL,NULL,'\0',NULL,NULL,NULL),(7,'Access to tabs','Access to tabs',NULL,NULL,'\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `high_level_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `line_items`
--

DROP TABLE IF EXISTS `line_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `line_items` (
  `LINE_ITEM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `PRIORITY` enum('Standard Normal','Standard High','Standard Low','Sponsorship','Network','Bulk','Price priority','House','Standard(Normal)','Standard(High)','Standard(Low)') DEFAULT NULL,
  `SPEC_TYPE_ID` bigint(20) DEFAULT NULL,
  `PROPOSAL_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `flight_end_date` datetime DEFAULT NULL,
  `flight_start_date` datetime DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `QUANTITY` decimal(10,2) DEFAULT NULL,
  `cpms` varchar(45) DEFAULT NULL,
  `avails` varchar(45) DEFAULT NULL,
  `proposed_cost` decimal(10,2) unsigned zerofill DEFAULT NULL,
  `delivery_impressions` enum('Evenly','Frontloaded','As fast as possible') DEFAULT NULL,
  `display_creatives` enum('Only one','One or more','As many as possible','All') DEFAULT NULL,
  `rotate_creatives` enum('Evenly','Optimized','Weighted','Sequential') DEFAULT NULL,
  `priority_value` int(11) DEFAULT '0',
  `RATE_TYPE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`LINE_ITEM_ID`),
  KEY `spec_type_fk_idx` (`SPEC_TYPE_ID`),
  KEY `proposal_lineitem_fk_idx` (`PROPOSAL_ID`),
  KEY `FK_6qgrt64ahrw2yfhils1fre3jn_idx` (`PRODUCT_ID`),
  KEY `rate_type_li_fk_idx` (`RATE_TYPE_ID`),
  CONSTRAINT `product_fk` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proposal_lineiitem_fk` FOREIGN KEY (`PROPOSAL_ID`) REFERENCES `proposal` (`PROPOSAL_ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `rate_type_li_fk` FOREIGN KEY (`RATE_TYPE_ID`) REFERENCES `rate_type` (`RATE_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `spec_type_fk` FOREIGN KEY (`SPEC_TYPE_ID`) REFERENCES `spec_type` (`SPEC_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `line_items`
--

LOCK TABLES `line_items` WRITE;
/*!40000 ALTER TABLE `line_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `line_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineitem_target`
--

DROP TABLE IF EXISTS `lineitem_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineitem_target` (
  `LINEITEM_TARGET_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LINEITEM_ID` bigint(20) DEFAULT NULL,
  `TARGET_TYPE` bigint(20) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`LINEITEM_TARGET_ID`),
  KEY `lineitem_target_type_fk_idx` (`TARGET_TYPE`),
  KEY `lineItem_id_fk_idx` (`LINEITEM_ID`),
  CONSTRAINT `lineItem_id_fk` FOREIGN KEY (`LINEITEM_ID`) REFERENCES `line_items` (`LINE_ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lineitem_target_type_fk` FOREIGN KEY (`TARGET_TYPE`) REFERENCES `audience_target_type` (`TARGET_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineitem_target`
--

LOCK TABLES `lineitem_target` WRITE;
/*!40000 ALTER TABLE `lineitem_target` DISABLE KEYS */;
/*!40000 ALTER TABLE `lineitem_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineitem_target_audience_target_values`
--

DROP TABLE IF EXISTS `lineitem_target_audience_target_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineitem_target_audience_target_values` (
  `LINEITEM_TARGET_ID` bigint(20) DEFAULT NULL,
  `audience_target_values_id` bigint(20) DEFAULT NULL,
  KEY `Audience_target_values_fk_idx` (`audience_target_values_id`),
  KEY `lineItem_target_id_fk_idx` (`LINEITEM_TARGET_ID`),
  CONSTRAINT `audience_target_values_id_fk` FOREIGN KEY (`audience_target_values_id`) REFERENCES `audience_target_values` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lineItem_target_id_fk` FOREIGN KEY (`LINEITEM_TARGET_ID`) REFERENCES `lineitem_target` (`LINEITEM_TARGET_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineitem_target_audience_target_values`
--

LOCK TABLES `lineitem_target_audience_target_values` WRITE;
/*!40000 ALTER TABLE `lineitem_target_audience_target_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `lineitem_target_audience_target_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newcreative`
--

DROP TABLE IF EXISTS `newcreative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newcreative` (
  `creativeid` bigint(20) NOT NULL AUTO_INCREMENT,
  `creative_name` varchar(100) DEFAULT NULL,
  `creative_description` varchar(300) DEFAULT NULL,
  `deviceid` int(11) NOT NULL,
  `custom_ad_iD` varchar(100) DEFAULT NULL,
  `created_by` bigint(10) NOT NULL,
  `creative_uploadlocation` varchar(100) DEFAULT NULL,
  `creative_uploadcaption` varchar(100) DEFAULT NULL,
  `creative_uploadsource` varchar(80) DEFAULT NULL,
  `creative_source_url` varchar(200) DEFAULT NULL,
  `creative_destination_url` varchar(200) DEFAULT NULL,
  `upload_satetime` datetime DEFAULT NULL,
  `creative_start_datetime` datetime DEFAULT NULL,
  `creative_end_datetime` datetime DEFAULT NULL,
  `created` bigint(10) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`creativeid`),
  KEY `fk_userid_idx` (`created_by`,`created`),
  KEY `userid_fk_idx` (`created_by`),
  CONSTRAINT `userid_fk` FOREIGN KEY (`created_by`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newcreative`
--

LOCK TABLES `newcreative` WRITE;
/*!40000 ALTER TABLE `newcreative` DISABLE KEYS */;
/*!40000 ALTER TABLE `newcreative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opportunity`
--

DROP TABLE IF EXISTS `opportunity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opportunity` (
  `OPPORTUNITY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `AGENCY_ID` bigint(20) DEFAULT NULL,
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `DUE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CURRENCY` enum('USD','INR','EUR') NOT NULL DEFAULT 'USD',
  `PRICING_MODEL` enum('Net','Gross') NOT NULL DEFAULT 'Net',
  `ADVERTISER_DISCOUNT` float DEFAULT '0',
  `BUDGET` float NOT NULL DEFAULT '0',
  `SALES_PERSON_ID` bigint(20) NOT NULL,
  `MEDIA_PANNER_ID` bigint(20) DEFAULT NULL,
  `TRAFFICKER_ID` bigint(20) NOT NULL,
  `billing_source_ID` bigint(20) DEFAULT NULL,
  `TERM_ID` bigint(20) DEFAULT NULL,
  `PERCENTAGE_OF_CLOSE` int(11) NOT NULL,
  `SALES_CATAGORY_ID` bigint(20) DEFAULT NULL,
  `NOTES` varchar(500) DEFAULT NULL,
  `ADVERTISER_ID` bigint(20) DEFAULT NULL,
  `SUBMITTED` bit(1) NOT NULL DEFAULT b'0',
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `assigned_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`OPPORTUNITY_ID`),
  KEY `billing_source_idx` (`billing_source_ID`),
  KEY `ADVERTISER_idx` (`ADVERTISER_ID`),
  KEY `AGENCY_idx` (`AGENCY_ID`),
  KEY `SALES_PERSON_idx` (`SALES_PERSON_ID`),
  KEY `traficker_fk_idx` (`TRAFFICKER_ID`),
  KEY `terms_opportunity_fk_idx` (`TERM_ID`),
  KEY `sales_catagory_fk_idx` (`SALES_CATAGORY_ID`),
  KEY `opportunity_user_fk_idx` (`assigned_by`),
  KEY `media_planner_fk_idx` (`MEDIA_PANNER_ID`),
  CONSTRAINT `ADVERTISERS` FOREIGN KEY (`ADVERTISER_ID`) REFERENCES `company` (`COMPANY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `AGENCY` FOREIGN KEY (`AGENCY_ID`) REFERENCES `company` (`COMPANY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `billing_source` FOREIGN KEY (`billing_source_ID`) REFERENCES `billing_source` (`billing_source_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `opportunity_media_planner_fk` FOREIGN KEY (`MEDIA_PANNER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `opportunity_user_fk` FOREIGN KEY (`assigned_by`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sales_catagory_fk` FOREIGN KEY (`SALES_CATAGORY_ID`) REFERENCES `sales_catagory` (`SALES_CATAGORY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sales_person_fk` FOREIGN KEY (`SALES_PERSON_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `terms_opportunity_fk` FOREIGN KEY (`TERM_ID`) REFERENCES `terms` (`term_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `traficker_fk` FOREIGN KEY (`TRAFFICKER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opportunity`
--

LOCK TABLES `opportunity` WRITE;
/*!40000 ALTER TABLE `opportunity` DISABLE KEYS */;
INSERT INTO `opportunity` VALUES (134,'test_proposal_18092017_1039','my proposal notes',109,'2017-09-17 18:30:00','2017-09-27 18:30:00','2017-09-19 18:30:00','USD','Net',NULL,344,2,410,6,1,NULL,34,4,NULL,107,'','2017-09-18 20:51:32',2,'\0','2017-09-18 20:51:44',2,2),(135,'test_proposal_18092017_1215',NULL,108,'2017-09-18 18:30:00','2017-09-19 18:30:00','2017-09-19 18:30:00','USD','Gross',45,3555,2,410,6,1,NULL,45,4,NULL,107,'','2017-09-18 22:14:46',2,'\0','2017-09-18 22:14:51',2,2),(136,'test_proposal_paras_09182017','New proposal',108,'2017-09-18 18:30:00','2017-09-29 18:30:00','2017-09-29 18:30:00','USD','Net',NULL,50000,410,410,6,1,NULL,30,1,NULL,107,'','2017-09-18 22:36:10',410,'\0','2017-09-18 22:36:36',410,410),(137,'testank_22_09','Test Description',122,'2017-10-06 18:30:00','2017-09-22 18:30:00','2017-09-24 18:30:00','INR','Net',NULL,100000,2,410,419,2,NULL,20,4,NULL,106,'','2017-09-23 01:09:08',2,'\0','2017-09-23 01:27:55',2,2),(138,'test_Ank_22_09',NULL,122,'2017-10-06 18:30:00','2017-09-22 18:30:00','2017-09-24 18:30:00','INR','Net',NULL,100000,2,410,419,2,NULL,20,4,NULL,106,'','2017-09-23 01:09:24',2,'\0','2017-09-23 01:12:08',2,2),(139,'dd',NULL,108,'2017-10-19 18:30:00','2017-10-25 18:30:00','2017-11-02 18:30:00','INR','Net',NULL,44,2,410,6,1,NULL,4,4,NULL,107,'\0','2017-10-07 07:01:41',2,'\0','2017-10-07 07:01:41',2,2);
/*!40000 ALTER TABLE `opportunity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opportunity_document`
--

DROP TABLE IF EXISTS `opportunity_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opportunity_document` (
  `OPPORTUNITY_DOC_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `PATH` varchar(500) DEFAULT NULL,
  `OPPORTUNITY_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`OPPORTUNITY_DOC_ID`),
  KEY `opportunity_doc_fk_idx` (`OPPORTUNITY_ID`),
  KEY `opportunity_doc_user_fk_idx` (`updated_by`),
  CONSTRAINT `opportunity_doc_opportunity_fk` FOREIGN KEY (`OPPORTUNITY_ID`) REFERENCES `opportunity` (`OPPORTUNITY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `opportunity_doc_user_fk` FOREIGN KEY (`updated_by`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opportunity_document`
--

LOCK TABLES `opportunity_document` WRITE;
/*!40000 ALTER TABLE `opportunity_document` DISABLE KEYS */;
INSERT INTO `opportunity_document` VALUES (3,'IMG_6970.JPG','JPG','/root/oms-qa/hotstar/oms/oms-media/target/~/oms-media/opportunity/IMG_6970-139.JPG',139,'2017-10-07 07:11:57',NULL,'2017-10-07 07:11:57',NULL);
/*!40000 ALTER TABLE `opportunity_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opportunity_version`
--

DROP TABLE IF EXISTS `opportunity_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opportunity_version` (
  `SEQ_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPPORTUNITY_ID` bigint(20) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `DUE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CURRENCY` enum('USD','INR','EUR') NOT NULL DEFAULT 'USD',
  `PRICING_MODEL` enum('Net','Gross') NOT NULL DEFAULT 'Net',
  `ADVERTISER_DISCOUNT` float DEFAULT '0',
  `BUDGET` float NOT NULL DEFAULT '0',
  `TERM_ID` bigint(20) DEFAULT NULL,
  `PERCENTAGE_OF_CLOSE` int(11) NOT NULL,
  `SALES_CATAGORY_ID` bigint(20) DEFAULT NULL,
  `NOTES` varchar(500) DEFAULT NULL,
  `SUBMITTED` bit(1) DEFAULT b'0',
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NO`),
  KEY `OPPORTUNITY_ID_idx` (`OPPORTUNITY_ID`),
  KEY `SALES_CATAGORY_idx` (`SALES_CATAGORY_ID`),
  KEY `TERM_OPP_VER_idx` (`TERM_ID`),
  CONSTRAINT `SALES_CATAGORIES` FOREIGN KEY (`SALES_CATAGORY_ID`) REFERENCES `sales_catagory` (`SALES_CATAGORY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `TERM_OPP_VER` FOREIGN KEY (`TERM_ID`) REFERENCES `terms` (`TERM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `opportunity` FOREIGN KEY (`OPPORTUNITY_ID`) REFERENCES `opportunity` (`OPPORTUNITY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opportunity_version`
--

LOCK TABLES `opportunity_version` WRITE;
/*!40000 ALTER TABLE `opportunity_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `opportunity_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_audience_target_values`
--

DROP TABLE IF EXISTS `order_audience_target_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_audience_target_values` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TARGET_TYPE_ID` bigint(20) DEFAULT NULL,
  `VALUE` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) NOT NULL DEFAULT b'1',
  `ORDER_LINE_ITEM_TARGET_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `target_idx` (`TARGET_TYPE_ID`),
  CONSTRAINT `order_target` FOREIGN KEY (`TARGET_TYPE_ID`) REFERENCES `audience_target_type` (`TARGET_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_audience_target_values`
--

LOCK TABLES `order_audience_target_values` WRITE;
/*!40000 ALTER TABLE `order_audience_target_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_audience_target_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_lineitem_target`
--

DROP TABLE IF EXISTS `order_lineitem_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_lineitem_target` (
  `ORDER_LINE_ITEM_TARGET_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ORDER_LINE_ITEM_ID` bigint(20) DEFAULT NULL,
  `TARGET_TYPE` bigint(20) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ORDER_LINE_ITEM_TARGET_ID`),
  KEY `orderlineitem_fk_idx` (`ORDER_LINE_ITEM_ID`),
  KEY `orderlineitem_target_type_fk_idx` (`TARGET_TYPE`),
  CONSTRAINT `orderlineitem_fk` FOREIGN KEY (`ORDER_LINE_ITEM_ID`) REFERENCES `order_lineitems` (`ORDER_LINE_ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `orderlineitem_target_type_fk` FOREIGN KEY (`TARGET_TYPE`) REFERENCES `audience_target_type` (`TARGET_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_lineitem_target`
--

LOCK TABLES `order_lineitem_target` WRITE;
/*!40000 ALTER TABLE `order_lineitem_target` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_lineitem_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_lineitem_target_audience_target_values`
--

DROP TABLE IF EXISTS `order_lineitem_target_audience_target_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_lineitem_target_audience_target_values` (
  `ORDER_LINE_ITEM_TARGET_ID` bigint(20) DEFAULT NULL,
  `order_audience_target_values_id` bigint(20) DEFAULT NULL,
  KEY `order_Audience_target_values_fk_idx` (`order_audience_target_values_id`),
  KEY `order_lineItem_target_id_fk_idx` (`ORDER_LINE_ITEM_TARGET_ID`),
  CONSTRAINT `order_audience_target_values_id_fk` FOREIGN KEY (`order_audience_target_values_id`) REFERENCES `audience_target_values` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_lineItem_target_id_fk` FOREIGN KEY (`ORDER_LINE_ITEM_TARGET_ID`) REFERENCES `order_lineitem_target` (`ORDER_LINE_ITEM_TARGET_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_lineitem_target_audience_target_values`
--

LOCK TABLES `order_lineitem_target_audience_target_values` WRITE;
/*!40000 ALTER TABLE `order_lineitem_target_audience_target_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_lineitem_target_audience_target_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_lineitems`
--

DROP TABLE IF EXISTS `order_lineitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_lineitems` (
  `ORDER_LINE_ITEM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LINE_ITEM_ID` bigint(20) NOT NULL,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `PRIORITY` enum('Standard Normal','Standard High','Standard Low','Sponsorship','Network','Bulk','Price priority','House') DEFAULT NULL,
  `SPEC_TYPE_ID` bigint(20) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  `PROPOSAL_ID` bigint(20) DEFAULT NULL,
  `CREATIVE_AD_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `flight_end_date` datetime DEFAULT NULL,
  `flight_start_date` datetime DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `QUANTITY` decimal(10,2) DEFAULT NULL,
  `cpms` varchar(45) DEFAULT NULL,
  `avails` varchar(45) DEFAULT NULL,
  `proposed_cost` decimal(10,2) unsigned zerofill DEFAULT NULL,
  `delivery_impressions` enum('Evenly','Frontloaded','As fast as possible') DEFAULT NULL,
  `display_creatives` enum('Only one','One or more','As many as possible','All') DEFAULT NULL,
  `rotate_creatives` enum('Evenly','Optimized','Weighted','Sequential') DEFAULT NULL,
  `priority_value` int(11) DEFAULT '0',
  `RATE_TYPE_ID` bigint(20) DEFAULT NULL,
  `creative_asset` bigint(20) DEFAULT NULL,
  `vpz_goal_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ORDER_LINE_ITEM_ID`),
  KEY `spec_type_fk_idx` (`SPEC_TYPE_ID`),
  KEY `proposal_lineitem_fk_idx` (`PROPOSAL_ID`),
  KEY `FK_6qgrt64ahrw2yfhils1fre3jn_idx` (`PRODUCT_ID`),
  KEY `rate_type_li_fk_idx` (`RATE_TYPE_ID`),
  KEY `creative_asset_fk_idx` (`creative_asset`),
  CONSTRAINT `creative_asset_fk` FOREIGN KEY (`creative_asset`) REFERENCES `creative_asset` (`creative_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_lineitems`
--

LOCK TABLES `order_lineitems` WRITE;
/*!40000 ALTER TABLE `order_lineitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_lineitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordertab`
--

DROP TABLE IF EXISTS `ordertab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordertab` (
  `ORDER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROPOSAL_ID` bigint(20) NOT NULL,
  `NAME` varchar(45) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `OPPORTUNITY_ID` bigint(20) DEFAULT NULL,
  `STATUS_ID` bigint(20) NOT NULL,
  `AGENCY_ID` bigint(20) DEFAULT NULL,
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `DUE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CURRENCY` enum('USD','INR','EUR') NOT NULL DEFAULT 'USD',
  `PRICING_MODEL` enum('Net','Gross') NOT NULL DEFAULT 'Net',
  `ADVERTISER_ID` bigint(20) DEFAULT NULL,
  `ADVERTISER_DISCOUNT` float unsigned zerofill DEFAULT '000000000000',
  `AGENCY_MARGIN` float unsigned zerofill DEFAULT NULL,
  `BUDGET` float unsigned zerofill NOT NULL DEFAULT '000000000000',
  `SALES_PERSON_ID` bigint(20) NOT NULL,
  `TRAFFICKER_ID` bigint(20) NOT NULL,
  `MEDIA_PANNER_ID` bigint(20) DEFAULT NULL,
  `billing_source_ID` bigint(20) DEFAULT NULL,
  `TERM_ID` bigint(20) DEFAULT NULL,
  `PERCENTAGE_OF_CLOSE` int(11) NOT NULL,
  `SALES_CATAGORY_ID` bigint(20) DEFAULT NULL,
  `SUBMITTED` bit(1) NOT NULL DEFAULT b'0',
  `NOTES` varchar(500) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint(20) DEFAULT NULL,
  `proposal_discount` float DEFAULT NULL,
  `planner_name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `assign_to` bigint(20) DEFAULT NULL,
  `pushed` bit(1) DEFAULT b'0',
  `vpz_campaign_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`,`PROPOSAL_ID`,`START_DATE`),
  KEY `ADVERTISER_idx` (`ADVERTISER_ID`),
  KEY `SALES_PERSON_idx` (`SALES_PERSON_ID`),
  KEY `TRAFFICKER_PROPOSAL_idx` (`TRAFFICKER_ID`),
  KEY `billing_source_PROPOSAL_idx` (`billing_source_ID`),
  KEY `TERM_ID_PROPOSAL_idx` (`TERM_ID`),
  KEY `AGENCY_PRO_idx` (`AGENCY_ID`),
  KEY `SALES_CATA_PRO_idx` (`SALES_CATAGORY_ID`),
  KEY `OPPORTUNITY_idx` (`OPPORTUNITY_ID`),
  KEY `proposal_status_fk_idx` (`STATUS_ID`),
  KEY `media_planner_fk_idx` (`MEDIA_PANNER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordertab`
--

LOCK TABLES `ordertab` WRITE;
/*!40000 ALTER TABLE `ordertab` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordertab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `PERMISSION_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DISPLAY_NAME` varchar(45) DEFAULT NULL,
  `VALUE` varchar(45) DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_DATE` date DEFAULT NULL,
  `HIGH_LEVEL_PERMISSION_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `permission_key` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PERMISSION_ID`),
  UNIQUE KEY `permission_key_UNIQUE` (`permission_key`),
  KEY `FK_kxf0if0ef4wqu6b8ysxabpkgg` (`HIGH_LEVEL_PERMISSION_ID`),
  CONSTRAINT `FK_kxf0if0ef4wqu6b8ysxabpkgg` FOREIGN KEY (`HIGH_LEVEL_PERMISSION_ID`) REFERENCES `high_level_permission` (`PERMISSION_COMPONENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'View users','1',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'view_users'),(4,'Edit users','1',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'edit_users'),(5,'View roles','2',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'view_roles'),(6,'Edit roles','3',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'edit_roles'),(7,'View products','4',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'view_products'),(8,'Edit products','4',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'edit_products'),(9,'View rate cards','5',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'view_rate_cards'),(10,'Edit rate cards','7',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'edit_rate_cards'),(11,'View creatives','6',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'view_creatives'),(12,'Edit creatives','6',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'edit_creatives'),(13,'View Ad units','7',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'view_ad_units'),(14,'Edit Ad units','3',NULL,NULL,NULL,NULL,1,NULL,'\0',NULL,NULL,'edit_ad_units'),(15,'Create opportunity','78',NULL,NULL,NULL,NULL,2,NULL,'\0',NULL,NULL,'create_opportunity'),(16,'View oportunity','3',NULL,NULL,NULL,NULL,2,NULL,'\0',NULL,NULL,'view_opportunity'),(17,'Create proposals','3',NULL,NULL,NULL,NULL,2,NULL,'\0',NULL,NULL,'create_proposal'),(18,'View proposals','test val1',NULL,NULL,NULL,NULL,2,NULL,'\0',NULL,NULL,'view_proposal'),(19,'Delivery','test val2',NULL,NULL,NULL,NULL,7,NULL,'\0',NULL,NULL,'delivery'),(20,'Admin','test val3',NULL,NULL,NULL,NULL,7,NULL,'\0',NULL,NULL,'admin'),(21,'Sales','test val14',NULL,NULL,NULL,NULL,7,NULL,'\0',NULL,NULL,'sales'),(22,'Financial','test val15',NULL,NULL,NULL,NULL,7,NULL,'\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premium`
--

DROP TABLE IF EXISTS `premium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `premium` (
  `PREMIUM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TARGET_TYPE_ID` bigint(20) DEFAULT NULL,
  `RATE_CARD_ID` bigint(20) DEFAULT NULL,
  `PREMIUM_PERCENTAGE` double DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PREMIUM_ID`),
  KEY `TARGET_TYPE_ID_idx` (`TARGET_TYPE_ID`),
  KEY `RATE_idx` (`RATE_CARD_ID`),
  CONSTRAINT `rate_card_premium` FOREIGN KEY (`RATE_CARD_ID`) REFERENCES `rate_card` (`RATE_CARD_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premium`
--

LOCK TABLES `premium` WRITE;
/*!40000 ALTER TABLE `premium` DISABLE KEYS */;
INSERT INTO `premium` VALUES (61,1,266,5,NULL,'2017-09-19 02:56:38',NULL,'\0','2017-09-19 02:56:38',NULL),(62,6,267,10,NULL,'2017-09-19 02:57:13',NULL,'\0','2017-09-19 02:57:13',NULL);
/*!40000 ALTER TABLE `premium` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `PRODUCT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `PRODUCT_TYPE_ID` bigint(20) DEFAULT NULL,
  `RATE_TYPE_ID` bigint(20) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT b'0',
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `delivery_impressions` enum('Evenly','Frontloaded','As fast as possible') DEFAULT NULL,
  `display_creatives` enum('Only one','One or more','As many as possible','All') DEFAULT NULL,
  `rotate_creatives` enum('Evenly','Optimized','Weighted','Sequential') DEFAULT NULL,
  `priority` enum('Standard Normal','Standard High','Standard Low','Sponsorship','Network','Bulk','Price priority','House') DEFAULT NULL,
  `priority_values` int(11) DEFAULT '0',
  PRIMARY KEY (`PRODUCT_ID`),
  KEY `product_type_idx` (`PRODUCT_TYPE_ID`),
  KEY `product_customer_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `product_customer_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (381,'Crickinfo India vs Aus Ad','This product will be delivered while broadcasting Boarder Gavaskar  trophy ',2,6,'','2017-09-18 20:21:16',14,'\0','2017-09-18 20:21:16',14,NULL,'Frontloaded','One or more','Optimized','Standard High',2),(382,'Citi Bank credit card Ad','This ad will be broadcast while user search in finance and banking category using data analytics . ',11,3,'','2017-09-18 20:24:11',14,'\0','2017-09-18 20:24:11',14,NULL,'As fast as possible','One or more','Evenly','Standard Low',1),(383,'Star Utasav new TV serial Ad','This product is not decided yet',2,1,'','2017-09-18 20:26:23',14,'\0','2017-09-18 20:26:23',14,NULL,'Evenly','One or more','Evenly','Standard Normal',1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_ad_unit`
--

DROP TABLE IF EXISTS `product_ad_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_ad_unit` (
  `PRODUCT_ID` bigint(20) NOT NULL,
  `ad_unit_ID` bigint(20) NOT NULL,
  KEY `FK_o46qeqxbgjyhfcyjee4s1qwu5` (`ad_unit_ID`),
  KEY `FK_mnr98hhdr0vq2p094ljqwpg7j` (`PRODUCT_ID`),
  CONSTRAINT `FK_mnr98hhdr0vq2p094ljqwpg7j` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_o46qeqxbgjyhfcyjee4s1qwu5` FOREIGN KEY (`ad_unit_ID`) REFERENCES `ad_unit` (`ad_unit_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_ad_unit`
--

LOCK TABLES `product_ad_unit` WRITE;
/*!40000 ALTER TABLE `product_ad_unit` DISABLE KEYS */;
INSERT INTO `product_ad_unit` VALUES (381,205),(381,206),(382,203),(382,204),(383,205);
/*!40000 ALTER TABLE `product_ad_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_creatives`
--

DROP TABLE IF EXISTS `product_creatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_creatives` (
  `PRODUCT_ID` bigint(20) NOT NULL,
  `CREATIVE_ID` bigint(20) NOT NULL,
  KEY `CREATIVE_idx` (`CREATIVE_ID`),
  KEY `product` (`PRODUCT_ID`),
  CONSTRAINT `FK_baouiak4sdm4s8yblbkpqpgib` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `creative` FOREIGN KEY (`CREATIVE_ID`) REFERENCES `creative` (`CREATIVE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_creatives`
--

LOCK TABLES `product_creatives` WRITE;
/*!40000 ALTER TABLE `product_creatives` DISABLE KEYS */;
INSERT INTO `product_creatives` VALUES (381,378),(382,379),(383,380);
/*!40000 ALTER TABLE `product_creatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_target`
--

DROP TABLE IF EXISTS `product_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_target` (
  `PRODUCT_TARGET_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `TARGET_TYPE` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`PRODUCT_TARGET_ID`),
  KEY `product_tatget_fk_idx` (`PRODUCT_ID`),
  KEY `target_type_fk_idx` (`TARGET_TYPE`),
  CONSTRAINT `product_tatget_fk` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `target_type_fk` FOREIGN KEY (`TARGET_TYPE`) REFERENCES `audience_target_type` (`TARGET_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_target`
--

LOCK TABLES `product_target` WRITE;
/*!40000 ALTER TABLE `product_target` DISABLE KEYS */;
INSERT INTO `product_target` VALUES (78,381,4,'\0'),(79,382,3,'\0'),(80,382,6,'\0'),(81,383,8,'\0'),(82,383,3,'\0'),(83,383,6,'\0');
/*!40000 ALTER TABLE `product_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_target_value`
--

DROP TABLE IF EXISTS `product_target_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_target_value` (
  `PRODUCT_TARGET_VALUE` bigint(20) DEFAULT NULL,
  `product_audience_target_values` bigint(20) DEFAULT NULL,
  KEY `product_target_fk1` (`PRODUCT_TARGET_VALUE`),
  KEY `audience_taret_values_idx` (`product_audience_target_values`),
  CONSTRAINT `audience_taret_values` FOREIGN KEY (`product_audience_target_values`) REFERENCES `audience_target_values` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_target_fk1` FOREIGN KEY (`PRODUCT_TARGET_VALUE`) REFERENCES `product_target` (`PRODUCT_TARGET_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_target_value`
--

LOCK TABLES `product_target_value` WRITE;
/*!40000 ALTER TABLE `product_target_value` DISABLE KEYS */;
INSERT INTO `product_target_value` VALUES (78,12),(78,14),(78,13),(79,9),(79,10),(79,11),(80,20),(80,19),(80,18),(81,26),(82,11),(83,18);
/*!40000 ALTER TABLE `product_target_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_type` (
  `TYPE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_NAME` varchar(45) DEFAULT NULL,
  `SUBTYPE_NAME` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(1) DEFAULT '0',
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES (1,'Print','Magazine',0,NULL,NULL,NULL,'\0',NULL,NULL),(2,'Digital','Video',0,NULL,NULL,NULL,'\0',NULL,NULL),(3,'Digital','Email',0,NULL,NULL,NULL,'\0',NULL,NULL),(4,'Digital','Search',0,NULL,NULL,NULL,'\0',NULL,NULL),(5,'Broadcast','Radio',0,NULL,NULL,NULL,'\0',NULL,NULL),(6,'Broadcast','Television',0,NULL,NULL,NULL,'\0',NULL,NULL),(7,'Direct','Post',0,NULL,NULL,NULL,'\0',NULL,NULL),(8,'Direct','Phone',0,NULL,NULL,NULL,'\0',NULL,NULL),(10,'Print','News Paper',0,NULL,NULL,NULL,'\0',NULL,NULL),(11,'Digital','Online display',0,NULL,NULL,NULL,'\0',NULL,NULL),(12,'Ditect','SMS',0,NULL,NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposal`
--

DROP TABLE IF EXISTS `proposal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal` (
  `PROPOSAL_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `OPPORTUNITY_ID` bigint(20) DEFAULT NULL,
  `STATUS_ID` bigint(20) NOT NULL,
  `AGENCY_ID` bigint(20) DEFAULT NULL,
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `DUE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CURRENCY` enum('USD','INR','EUR') NOT NULL DEFAULT 'USD',
  `PRICING_MODEL` enum('Net','Gross') NOT NULL DEFAULT 'Net',
  `ADVERTISER_ID` bigint(20) DEFAULT NULL,
  `ADVERTISER_DISCOUNT` float unsigned zerofill DEFAULT '000000000000',
  `AGENCY_MARGIN` float unsigned zerofill DEFAULT NULL,
  `BUDGET` float unsigned zerofill NOT NULL DEFAULT '000000000000',
  `SALES_PERSON_ID` bigint(20) NOT NULL,
  `TRAFFICKER_ID` bigint(20) NOT NULL,
  `MEDIA_PANNER_ID` bigint(20) DEFAULT NULL,
  `billing_source_ID` bigint(20) DEFAULT NULL,
  `TERM_ID` bigint(20) DEFAULT NULL,
  `PERCENTAGE_OF_CLOSE` int(11) NOT NULL,
  `SALES_CATAGORY_ID` bigint(20) DEFAULT NULL,
  `SUBMITTED` bit(1) NOT NULL DEFAULT b'0',
  `NOTES` varchar(500) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint(20) DEFAULT NULL,
  `proposal_discount` float DEFAULT NULL,
  `planner_name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `assign_to` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PROPOSAL_ID`,`START_DATE`),
  KEY `ADVERTISER_idx` (`ADVERTISER_ID`),
  KEY `SALES_PERSON_idx` (`SALES_PERSON_ID`),
  KEY `TRAFFICKER_PROPOSAL_idx` (`TRAFFICKER_ID`),
  KEY `billing_source_PROPOSAL_idx` (`billing_source_ID`),
  KEY `TERM_ID_PROPOSAL_idx` (`TERM_ID`),
  KEY `AGENCY_PRO_idx` (`AGENCY_ID`),
  KEY `SALES_CATA_PRO_idx` (`SALES_CATAGORY_ID`),
  KEY `OPPORTUNITY_idx` (`OPPORTUNITY_ID`),
  KEY `proposal_status_fk_idx` (`STATUS_ID`),
  KEY `media_planner_fk_idx` (`MEDIA_PANNER_ID`),
  CONSTRAINT `ADVERTISER` FOREIGN KEY (`ADVERTISER_ID`) REFERENCES `company` (`COMPANY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `AGENCY_PRO` FOREIGN KEY (`AGENCY_ID`) REFERENCES `company` (`COMPANY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `PRO_OPPORTUNITY` FOREIGN KEY (`OPPORTUNITY_ID`) REFERENCES `opportunity` (`OPPORTUNITY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `billing_source_PROPOSAL` FOREIGN KEY (`billing_source_ID`) REFERENCES `billing_source` (`billing_source_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `media_planner_fk` FOREIGN KEY (`MEDIA_PANNER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proposal_status_fk` FOREIGN KEY (`STATUS_ID`) REFERENCES `proposal_status` (`STATUS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sales_catagory_product_fk` FOREIGN KEY (`SALES_CATAGORY_ID`) REFERENCES `sales_catagory` (`SALES_CATAGORY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sales_person_proposal_fk` FOREIGN KEY (`SALES_PERSON_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `terms_proposal_fk` FOREIGN KEY (`TERM_ID`) REFERENCES `terms` (`term_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `traficker_proposal_fk` FOREIGN KEY (`TRAFFICKER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposal`
--

LOCK TABLES `proposal` WRITE;
/*!40000 ALTER TABLE `proposal` DISABLE KEYS */;
/*!40000 ALTER TABLE `proposal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposal_audit`
--

DROP TABLE IF EXISTS `proposal_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_audit` (
  `proposal_audit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(45) NOT NULL,
  `end_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  `proposal_version_id` bigint(20) DEFAULT NULL,
  `proposal_status` bigint(20) DEFAULT NULL,
  `proposal_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`proposal_audit_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `proposal_status_idx` (`proposal_status`),
  CONSTRAINT `proposal_status` FOREIGN KEY (`proposal_status`) REFERENCES `proposal_status` (`STATUS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=560 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposal_audit`
--

LOCK TABLES `proposal_audit` WRITE;
/*!40000 ALTER TABLE `proposal_audit` DISABLE KEYS */;
INSERT INTO `proposal_audit` VALUES (394,'New Proposal has been created!','2017-09-18 15:21:44',2,NULL,1,283),(395,'proposal resaved','2017-09-18 15:26:38',410,NULL,1,283),(396,'document added','2017-09-18 15:27:20',410,NULL,1,283),(397,'In progress','2017-09-18 05:34:00',410,0,6,283),(398,'Pricing Review','2017-09-18 05:35:12',410,0,6,283),(399,'Pricing Approved','2017-09-18 05:36:27',1,0,4,283),(400,'Legal Review','2017-09-18 15:36:07',410,0,6,283),(401,'Legal Approved','2017-09-18 15:38:24',9,0,5,283),(402,'Admin Review','2017-09-18 15:38:49',410,0,6,283),(403,'New Proposal has been created!','2017-09-18 16:44:51',2,NULL,1,284),(404,'New Proposal has been created!','2017-09-18 17:06:36',410,NULL,1,285),(405,'In progress','2017-09-18 17:10:14',410,0,6,285),(406,'New Proposal has been created!','2017-09-19 20:32:32',2,NULL,1,286),(407,'In progress','2017-09-19 20:36:00',2,0,6,286),(408,'In progress','2017-09-21 17:45:41',2,0,6,284),(409,'Pricing Review','2017-09-21 17:46:37',410,0,6,284),(410,'Pricing Approved','2017-09-21 17:47:07',1,0,4,284),(411,'Legal Review','2017-09-21 17:47:31',410,0,6,284),(412,'New Proposal has been created!','2017-09-22 19:42:07',2,NULL,1,287),(413,'New Proposal has been created!','2017-09-22 19:57:55',2,NULL,1,288),(414,'New Proposal has been created!','2017-10-05 18:06:42',2,NULL,1,289),(415,'In progress','2017-10-05 18:11:42',2,0,6,289),(416,'New Proposal has been created!','2017-10-05 22:14:15',2,NULL,1,290),(417,'In progress','2017-10-05 22:14:33',2,0,6,290),(418,'Pricing Review','2017-10-05 22:22:01',410,0,6,290),(419,'Pricing Approved','2017-10-05 22:23:19',1,0,4,290),(420,'Legal Review','2017-10-05 22:23:59',410,0,6,290),(421,'Legal Approved','2017-10-05 22:24:29',9,0,5,290),(422,'Admin Review','2017-10-05 22:24:51',410,0,6,290),(423,'New Proposal has been created!','2017-10-05 22:28:22',2,NULL,1,291),(424,'In progress','2017-10-05 22:28:49',2,0,6,291),(425,'Pricing Review','2017-10-05 22:30:59',410,0,6,291),(426,'Pricing Approved','2017-10-05 22:31:40',1,0,4,291),(427,'Legal Review','2017-10-05 22:32:12',410,0,6,291),(428,'Legal Approved','2017-10-05 22:33:50',9,0,5,291),(429,'Admin Review','2017-10-05 22:37:50',410,0,6,291),(430,'fxs','2017-10-06 21:32:45',2,NULL,5,284),(431,'sdf','2017-10-06 22:36:50',2,NULL,5,284),(432,'New Proposal has been created!','2017-10-07 07:05:55',2,NULL,1,292),(433,'34','2017-10-07 07:06:45',2,NULL,1,292),(434,'In progress','2017-10-07 07:07:11',2,0,6,292),(435,'Pricing Review','2017-10-07 07:07:36',410,0,6,292),(436,'Pricing Approved','2017-10-07 07:08:23',1,0,4,292),(437,'Legal Review','2017-10-07 07:08:51',410,0,6,292),(438,'Legal Approved','2017-10-07 07:38:10',9,0,5,292),(439,'Admin Review','2017-10-07 07:38:37',410,0,6,292),(440,'New Proposal has been created!','2017-10-07 07:46:20',2,NULL,1,293),(441,'23','2017-10-07 07:47:03',2,NULL,1,293),(442,'In progress','2017-10-07 07:47:30',2,0,6,293),(443,'Pricing Review','2017-10-07 07:48:05',410,0,6,293),(444,'Pricing Approved','2017-10-07 07:48:44',1,0,4,293),(445,'Legal Review','2017-10-07 07:49:18',410,0,6,293),(446,'New Proposal has been created!','2017-10-07 08:17:20',2,NULL,1,294),(447,'In progress','2017-10-07 08:17:29',2,0,6,294),(448,'Pricing Review','2017-10-07 08:17:58',411,0,6,294),(449,'Pricing Approved','2017-10-07 08:18:34',1,0,4,294),(450,'sdfg','2017-10-07 08:30:13',411,NULL,6,294),(451,'Legal Review','2017-10-07 08:30:38',411,0,6,294),(452,'Legal Approved','2017-10-07 08:30:55',9,0,5,293),(453,'Admin Review','2017-10-07 08:31:16',410,0,6,293),(454,'New Proposal has been created!','2017-10-07 09:38:04',2,NULL,1,295),(455,'34','2017-10-07 09:39:31',410,NULL,1,295),(456,'In progress','2017-10-07 09:39:42',410,0,6,295),(457,'Pricing Review','2017-10-07 09:40:05',410,0,6,295),(458,'Pricing Approved','2017-10-07 09:43:12',1,0,4,295),(459,'Legal Review','2017-10-07 09:44:00',410,0,6,295),(460,'Legal Approved','2017-10-07 09:46:19',9,0,5,295),(461,'Admin Review','2017-10-07 09:47:29',410,0,6,295),(462,'New Proposal has been created!','2017-10-07 09:53:00',2,NULL,1,296),(463,'New Proposal has been created!','2017-10-08 14:47:13',2,NULL,1,297),(464,'wwqwqw','2017-10-08 14:49:11',2,NULL,1,297),(465,'www','2017-10-08 15:49:14',2,NULL,1,297),(466,'fg','2017-10-08 15:57:24',2,NULL,1,297),(467,'fff','2017-10-08 15:59:12',2,NULL,1,297),(468,'www','2017-10-08 16:08:46',2,NULL,1,297),(469,'sdsd','2017-10-08 16:12:05',2,NULL,1,297),(470,'New Proposal has been created!','2017-10-09 06:57:14',2,NULL,1,298),(471,'www','2017-10-09 06:58:28',2,NULL,1,298),(472,'New Proposal has been created!','2017-10-09 19:00:17',2,NULL,1,299),(473,'proposal line item added','2017-10-09 19:01:30',410,NULL,1,299),(474,'In progress','2017-10-09 19:01:54',410,0,6,299),(475,'Pricing Review','2017-10-09 19:02:08',410,0,6,299),(476,'Pricing Approved','2017-10-09 19:02:52',1,0,4,299),(477,'Legal Review','2017-10-09 19:03:23',410,0,6,299),(478,'Legal Approved','2017-10-09 19:04:08',9,0,5,299),(479,'Admin Review','2017-10-09 19:04:52',410,0,6,299),(480,'New Proposal has been created!','2017-10-09 19:28:34',2,NULL,1,300),(481,'fgadr','2017-10-09 19:30:00',2,NULL,1,300),(482,'fdgh','2017-10-09 19:37:52',2,NULL,1,300),(483,'dfg','2017-10-09 19:38:13',2,NULL,1,300),(484,'New Proposal has been created!','2017-10-09 10:13:58',2,NULL,1,301),(485,'vpz campaign saved','2017-10-09 10:15:50',2,NULL,1,301),(486,'In progress','2017-10-09 10:17:01',410,0,6,301),(487,'Pricing Review','2017-10-09 10:17:12',410,0,6,301),(488,'Pricing Approved','2017-10-09 10:17:54',1,0,4,301),(489,'Legal Review','2017-10-09 10:18:27',410,0,6,301),(490,'Legal Approved','2017-10-09 10:19:18',9,0,5,301),(491,'Admin Review','2017-10-09 10:20:02',410,0,6,301),(492,'New Proposal has been created!','2017-10-09 21:00:01',2,NULL,1,302),(493,'drgt','2017-10-09 21:00:35',2,NULL,1,302),(494,'dfgg','2017-10-09 21:01:29',2,NULL,1,302),(495,'New Proposal has been created!','2017-10-09 21:05:27',2,NULL,1,303),(496,'rtyh','2017-10-09 21:10:09',2,NULL,1,302),(498,'In progress','2017-10-09 21:17:31',2,0,6,303),(499,'aed','2017-10-09 21:37:40',2,NULL,1,302),(500,'New Proposal has been created!','2017-10-09 21:40:26',2,NULL,1,304),(503,'saving after deleting line item','2017-10-09 21:43:41',411,NULL,1,304),(506,'In progress','2017-10-09 21:54:17',411,0,6,304),(507,'Pricing Review','2017-10-09 21:54:24',411,0,6,304),(508,'test','2017-10-09 21:58:50',1,NULL,4,304),(509,'Pricing Review','2017-10-09 22:00:12',1,NULL,4,304),(510,'Pricing Approved','2017-10-09 22:00:23',1,0,4,304),(511,'Legal Review','2017-10-09 22:01:09',411,0,6,304),(512,'Legal Approved','2017-10-09 22:02:02',9,0,5,304),(513,'Admin Review','2017-10-09 22:02:34',411,0,6,304),(514,'New Proposal has been created!','2017-10-09 22:12:39',2,NULL,1,305),(515,'New Proposal has been created!','2017-10-09 22:12:53',2,NULL,1,306),(516,'reset assigned  to','2017-10-09 22:29:20',2,NULL,1,305),(517,'New Proposal has been created!','2017-10-09 22:32:09',2,NULL,1,307),(518,'Saving first Line item','2017-10-09 22:33:44',2,NULL,1,307),(520,'Saving line item two','2017-10-09 22:35:05',2,NULL,1,307),(521,'Saving Proposal after saving line item','2017-10-09 22:35:39',2,NULL,1,307),(522,'In progress','2017-10-09 22:35:47',2,0,6,307),(523,'Pricing Review','2017-10-09 22:36:11',411,0,6,307),(524,'Pricing Approved','2017-10-09 22:37:50',1,0,4,307),(525,'Legal Review','2017-10-09 22:38:45',411,0,6,307),(526,'Legal Approved','2017-10-09 22:39:26',9,0,5,307),(527,'Admin Review','2017-10-09 22:40:05',411,0,6,307),(528,'New Proposal has been created!','2017-10-09 13:36:58',410,NULL,1,308),(529,'34','2017-10-09 13:38:20',410,NULL,1,308),(530,'In progress','2017-10-09 13:38:33',410,0,6,308),(531,'Pricing Review','2017-10-09 13:38:42',410,0,6,308),(532,'Pricing Approved','2017-10-09 13:39:24',1,0,4,308),(533,'Legal Review','2017-10-09 13:39:54',410,0,6,308),(534,'Legal Approved','2017-10-09 13:40:39',9,0,5,308),(535,'Admin Review','2017-10-09 13:41:15',410,0,6,308),(536,'New Proposal has been created!','2017-10-09 15:03:47',2,NULL,1,309),(537,'333','2017-10-09 15:05:32',410,NULL,1,309),(538,'In progress','2017-10-09 15:05:47',410,0,6,309),(539,'Pricing Review','2017-10-09 15:05:55',410,0,6,309),(540,'Pricing Approved','2017-10-09 15:06:37',1,0,4,309),(541,'Legal Review','2017-10-09 15:07:13',410,0,6,309),(542,'Legal Approved','2017-10-09 15:08:06',9,0,5,309),(543,'Admin Review','2017-10-09 15:08:49',410,0,6,309),(544,'New Proposal has been created!','2017-10-09 15:37:26',2,NULL,1,310),(545,'455','2017-10-09 15:39:28',410,NULL,1,310),(546,'In progress','2017-10-09 15:39:41',410,0,6,310),(547,'Pricing Review','2017-10-09 15:39:49',410,0,6,310),(548,'Pricing Approved','2017-10-09 15:40:42',1,0,4,310),(549,'Legal Review','2017-10-09 15:41:15',410,0,6,310),(550,'Legal Approved','2017-10-09 15:42:08',9,0,5,310),(551,'Admin Review','2017-10-09 15:42:42',410,0,6,310),(552,'New Proposal has been created!','2017-10-10 04:19:19',2,NULL,1,311),(553,'In progress','2017-10-10 04:19:54',410,0,6,311),(554,'34','2017-10-10 04:20:47',410,NULL,6,311),(555,'Pricing Review','2017-10-10 04:21:02',410,0,6,311),(556,'Pricing Approved','2017-10-10 04:24:32',1,0,4,311),(557,'Legal Review','2017-10-10 04:25:18',410,0,6,311),(558,'Legal Approved','2017-10-10 04:26:15',9,0,5,311),(559,'Admin Review','2017-10-10 04:27:00',410,0,6,311);
/*!40000 ALTER TABLE `proposal_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposal_document`
--

DROP TABLE IF EXISTS `proposal_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_document` (
  `PROPOSAL_DOC_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `PATH` varchar(500) DEFAULT NULL,
  `PROPOSAL_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PROPOSAL_DOC_ID`),
  KEY `proposal_doc_fk_idx` (`PROPOSAL_ID`),
  KEY `proposal_doc_user_fk_idx` (`updated_by`),
  KEY `proposal_doc_createdBy_fk_idx` (`created_by`),
  CONSTRAINT `proposal_doc_createdBy_fk` FOREIGN KEY (`created_by`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proposal_doc_proposal_fk` FOREIGN KEY (`PROPOSAL_ID`) REFERENCES `proposal` (`PROPOSAL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proposal_doc_user_fk` FOREIGN KEY (`updated_by`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposal_document`
--

LOCK TABLES `proposal_document` WRITE;
/*!40000 ALTER TABLE `proposal_document` DISABLE KEYS */;
/*!40000 ALTER TABLE `proposal_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposal_status`
--

DROP TABLE IF EXISTS `proposal_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_status` (
  `STATUS_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`STATUS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposal_status`
--

LOCK TABLES `proposal_status` WRITE;
/*!40000 ALTER TABLE `proposal_status` DISABLE KEYS */;
INSERT INTO `proposal_status` VALUES (1,'Draft',NULL,NULL,NULL,'\0',NULL,NULL),(2,'Pending',NULL,NULL,NULL,'\0',NULL,NULL),(3,'Sold',NULL,NULL,NULL,'\0',NULL,NULL),(4,'Pricing Review',NULL,NULL,NULL,'\0',NULL,NULL),(5,'Legal Review',NULL,NULL,NULL,'\0',NULL,NULL),(6,'In progress',NULL,NULL,NULL,'\0',NULL,NULL),(7,'Admin Review',NULL,NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `proposal_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposal_version`
--

DROP TABLE IF EXISTS `proposal_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_version` (
  `SEQ_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROPOSAL_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `OPPORTUNITY_ID` bigint(20) DEFAULT NULL,
  `STATUS` bigint(20) NOT NULL DEFAULT '1',
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `DUE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CURRENCY` enum('USD','INR','EUR') NOT NULL DEFAULT 'USD',
  `PRICING_MODEL` enum('Net','Gross') NOT NULL DEFAULT 'Net',
  `ADVERTISER_DISCOUNT` float DEFAULT '0',
  `BUDGET` float NOT NULL DEFAULT '0',
  `TERM_ID` bigint(20) DEFAULT NULL,
  `PERCENTAGE_OF_CLOSE` int(11) NOT NULL,
  `SALES_CATAGORY_ID` bigint(20) DEFAULT NULL,
  `SUBMITTED` bit(1) NOT NULL DEFAULT b'0',
  `NOTES` varchar(500) DEFAULT NULL,
  `status_id` bigint(20) DEFAULT NULL,
  `proposal_discount` float DEFAULT NULL,
  `previous_status` bigint(20) DEFAULT NULL,
  `status_desc` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NO`),
  KEY `PROPOSAL_idx` (`PROPOSAL_ID`),
  KEY `SALES_CATAGORIES_idx` (`SALES_CATAGORY_ID`),
  KEY `OPPORTUNITIES` (`OPPORTUNITY_ID`),
  KEY `FK_l19wrdc0u9mbmwmy4yveojx31` (`status_id`),
  KEY `terms_proposal_version_fk_idx` (`TERM_ID`),
  KEY `prev_status_fk` (`previous_status`),
  CONSTRAINT `FK_l19wrdc0u9mbmwmy4yveojx31` FOREIGN KEY (`status_id`) REFERENCES `proposal_status` (`STATUS_ID`),
  CONSTRAINT `OPPORTUNITIES` FOREIGN KEY (`OPPORTUNITY_ID`) REFERENCES `opportunity` (`OPPORTUNITY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `prev_status_fk` FOREIGN KEY (`previous_status`) REFERENCES `proposal_status` (`STATUS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proposal` FOREIGN KEY (`PROPOSAL_ID`) REFERENCES `proposal` (`PROPOSAL_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sales_catagory_propsa_version_fk` FOREIGN KEY (`SALES_CATAGORY_ID`) REFERENCES `sales_catagory` (`SALES_CATAGORY_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `terms_proposal_version_fk` FOREIGN KEY (`TERM_ID`) REFERENCES `terms` (`term_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposal_version`
--

LOCK TABLES `proposal_version` WRITE;
/*!40000 ALTER TABLE `proposal_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `proposal_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rate_card`
--

DROP TABLE IF EXISTS `rate_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate_card` (
  `RATE_CARD_ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `CREATED_DATE` date DEFAULT NULL,
  `MODIFIED_DATE` date DEFAULT NULL,
  `SALES_CATEGORY_NAME` varchar(200) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_DISPLAY_NAME` varchar(200) DEFAULT NULL,
  `BASE_PRICE` decimal(10,0) DEFAULT NULL,
  `SECTIONS_NAME` varchar(2000) DEFAULT NULL,
  `NOTES` varchar(2000) DEFAULT NULL,
  `IS_ACTIVE` bit(1) DEFAULT b'0',
  `VERSION` int(11) DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `MODIFIED_BY` varchar(100) DEFAULT NULL,
  `IS_RATECARDROUNDED` bit(1) DEFAULT b'0',
  `created` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`RATE_CARD_ID`),
  KEY `rate_card_profile_ibfk_1` (`PRODUCT_ID`),
  CONSTRAINT `RATE_CARD_PROFILE_ibfk_1` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rate_card`
--

LOCK TABLES `rate_card` WRITE;
/*!40000 ALTER TABLE `rate_card` DISABLE KEYS */;
INSERT INTO `rate_card` VALUES (266,NULL,NULL,NULL,382,NULL,567,NULL,'citi bank ad','\0',NULL,'2',NULL,'\0','2017-09-18 21:13:38','\0','2017-09-19 02:56:38',410),(267,NULL,NULL,NULL,381,NULL,600000,NULL,'rates can be changed as suggested by sales team','\0',NULL,'410',NULL,'\0','2017-09-19 02:33:00','\0','2017-09-21 23:01:56',2);
/*!40000 ALTER TABLE `rate_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rate_card_ad_unit`
--

DROP TABLE IF EXISTS `rate_card_ad_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate_card_ad_unit` (
  `RATE_CARD_ID` bigint(20) DEFAULT NULL,
  `ad_unit_ID` bigint(20) DEFAULT NULL,
  KEY `RATE_CARD_idx` (`RATE_CARD_ID`),
  KEY `ad_unit_idx` (`ad_unit_ID`),
  CONSTRAINT `ad_unit` FOREIGN KEY (`ad_unit_ID`) REFERENCES `ad_unit` (`ad_unit_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rate_card_fk1` FOREIGN KEY (`RATE_CARD_ID`) REFERENCES `rate_card` (`RATE_CARD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rate_card_ad_unit`
--

LOCK TABLES `rate_card_ad_unit` WRITE;
/*!40000 ALTER TABLE `rate_card_ad_unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `rate_card_ad_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rate_type`
--

DROP TABLE IF EXISTS `rate_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate_type` (
  `RATE_TYPE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT b'0',
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`RATE_TYPE_ID`),
  KEY `rate_type_customer_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `rate_type_customer_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rate_type`
--

LOCK TABLES `rate_type` WRITE;
/*!40000 ALTER TABLE `rate_type` DISABLE KEYS */;
INSERT INTO `rate_type` VALUES (1,'CPC','Cost Per Click','\0',NULL,NULL,'\0',NULL,NULL,NULL),(2,'CPL','Cost Per Lead','\0',NULL,NULL,'\0',NULL,NULL,NULL),(3,'CPI','Cost Per Inch','\0',NULL,NULL,'\0',NULL,NULL,NULL),(4,'CPV','Cost Per View','\0',NULL,NULL,'\0',NULL,NULL,NULL),(5,'CPM','Cost Per Mille','\0',NULL,NULL,'\0',NULL,NULL,NULL),(6,'CPD','Cost Per Day','\0',NULL,NULL,'\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `rate_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `active` bit(1) DEFAULT b'0',
  `created` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`),
  KEY `role_customer_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `role_customer_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Administrator','Will have full access to application',NULL,NULL,NULL,NULL,'',NULL,'\0','2017-06-12 10:33:16',14,2),(2,'Media Planner','Proposal planning activities',NULL,NULL,NULL,NULL,'',NULL,'\0','2017-06-12 10:33:56',14,NULL),(3,'Sales Manager','Sales Manager',NULL,NULL,NULL,NULL,'',NULL,'\0',NULL,14,NULL),(4,'Pricing Manager','Define rates of products to sell',NULL,NULL,NULL,NULL,'',NULL,'\0','2017-06-12 10:35:27',14,NULL),(5,'Legal','Legal',NULL,NULL,NULL,NULL,'',NULL,'\0',NULL,14,NULL),(271,'Trafficker','To schedule and optimise campaigns ',NULL,NULL,NULL,NULL,'',NULL,'\0','2017-06-13 17:02:31',11,NULL),(272,'Sales Person','Handle opportunities and sales',NULL,NULL,NULL,NULL,'',NULL,'\0','2017-09-08 17:26:58',14,NULL),(276,'Project Manager','PMU',NULL,NULL,NULL,NULL,'',NULL,'\0','2017-09-08 17:09:52',14,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `ROLE_ID` bigint(20) NOT NULL,
  `PERMISSION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`),
  KEY `FK_PERMISSION` (`PERMISSION_ID`),
  CONSTRAINT `FK_PERMISSION` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permission` (`PERMISSION_ID`),
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (272,1),(276,1),(272,4),(276,4),(272,5),(276,5),(272,6),(276,6),(272,7),(276,7),(272,8),(276,8),(272,9),(276,9),(272,10),(276,10),(272,11),(276,11),(272,12),(276,12),(272,13),(276,13),(272,14),(276,14),(272,15),(272,16),(272,17),(272,18),(272,19),(272,20),(276,20),(272,21),(276,21),(272,22),(276,22);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_catagory`
--

DROP TABLE IF EXISTS `sales_catagory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales_catagory` (
  `SALES_CATAGORY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`SALES_CATAGORY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_catagory`
--

LOCK TABLES `sales_catagory` WRITE;
/*!40000 ALTER TABLE `sales_catagory` DISABLE KEYS */;
INSERT INTO `sales_catagory` VALUES (1,'Books','',NULL,NULL,'\0',NULL,NULL),(2,'Business','',NULL,NULL,'\0',NULL,NULL),(3,'Dining','',NULL,NULL,'\0',NULL,NULL),(4,'Automobile','',NULL,NULL,'\0',NULL,NULL),(5,'Entertinment','',NULL,NULL,'\0',NULL,NULL),(6,'Financial','',NULL,NULL,'\0',NULL,NULL),(7,'Hotels','',NULL,NULL,'\0',NULL,NULL),(8,'Fashion','',NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `sales_catagory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seasonal_discount`
--

DROP TABLE IF EXISTS `seasonal_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seasonal_discount` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `RATE_CARD_ID` bigint(11) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `DISCOUNT` decimal(10,0) DEFAULT NULL,
  `IS_NOT` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `RULE_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `rate_card_idx` (`RATE_CARD_ID`),
  CONSTRAINT `rate_card_id` FOREIGN KEY (`RATE_CARD_ID`) REFERENCES `rate_card` (`RATE_CARD_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seasonal_discount`
--

LOCK TABLES `seasonal_discount` WRITE;
/*!40000 ALTER TABLE `seasonal_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `seasonal_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spec_type`
--

DROP TABLE IF EXISTS `spec_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spec_type` (
  `SPEC_ID` bigint(20) NOT NULL DEFAULT '1',
  `NAME` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`SPEC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spec_type`
--

LOCK TABLES `spec_type` WRITE;
/*!40000 ALTER TABLE `spec_type` DISABLE KEYS */;
INSERT INTO `spec_type` VALUES (1,'HTML5',NULL,NULL,'\0',NULL,NULL),(2,'Rich text',NULL,NULL,'\0',NULL,NULL),(3,'Video',NULL,NULL,'\0',NULL,NULL),(4,'Image',NULL,NULL,'\0',NULL,NULL);
/*!40000 ALTER TABLE `spec_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terms`
--

DROP TABLE IF EXISTS `terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terms` (
  `term_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`term_id`),
  KEY `terms_customer_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `terms_customer_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terms`
--

LOCK TABLES `terms` WRITE;
/*!40000 ALTER TABLE `terms` DISABLE KEYS */;
INSERT INTO `terms` VALUES (1,NULL,NULL,'\0',NULL,NULL,'test desc','test name',2);
/*!40000 ALTER TABLE `terms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `USER_ID` bigint(10) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` varchar(45) DEFAULT NULL,
  `LAST_NAME` varchar(45) DEFAULT NULL,
  `FIRST_NAME` varchar(45) DEFAULT NULL,
  `EMAIL_ID` varchar(45) NOT NULL,
  `ADDRESS_1` varchar(100) DEFAULT NULL,
  `ADDRESS_2` varchar(100) DEFAULT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `STATE` varchar(45) DEFAULT NULL,
  `ZIP` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `TELEPHONE` varchar(45) DEFAULT NULL,
  `FAX` varchar(45) DEFAULT NULL,
  `MOBILE` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `active` char(1) NOT NULL DEFAULT 'T',
  `ROLE_ID` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `credentialsexpired` bit(1) NOT NULL,
  `expired` bit(1) NOT NULL,
  `locked` bit(1) NOT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `source` enum('internal','register','social') DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `EMAIL_ID_UNIQUE` (`EMAIL_ID`),
  KEY `ROLE_ID_idx` (`ROLE_ID`),
  KEY `user_customer_fk_idx` (`CUSTOMER_ID`),
  CONSTRAINT `FK_qleu8ddawkdltal07p8e6hgva` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`),
  CONSTRAINT `user_customer_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=429 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'vikas.parashar','Parashar','Vikas','vikas.parashar@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62','delhi 1','Noida','UP','201309','IN','+91 (120) 4030300, 6043',NULL,'+91 9999655850',NULL,'2017-04-26',NULL,'2017-05-09','1',4,NULL,'\0','2017-06-01 12:32:52',NULL,'\0','\0','\0',NULL,NULL,NULL,NULL),(2,'ritesh.nailwal','nailwal','Ritesh','ritesh.nailwal@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN','+91 (80) 30596300, 3218',NULL,'+91 9986571110',NULL,'2017-04-28',NULL,'2017-05-03','1',1,NULL,'\0','2017-06-01 15:04:48',NULL,'\0','\0','\0',NULL,NULL,NULL,NULL),(3,'satya.singh','Singh','Satya Prakash','satya.singh@tavant.com','Okaya Centre, Tower 1, 5th Floor B-5 Sector 62','NOIDA','Noida','UP','201309','IN','+91 (120) 4030300, 6043',NULL,'+91 9415019295',NULL,'2017-05-03',NULL,'2017-05-12','1',3,NULL,'','2017-06-03 15:47:21',NULL,'\0','\0','\0',NULL,NULL,NULL,NULL),(5,'shubhram.krishna','krishna','shubhram','shubhram.krishna@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',271,NULL,'\0',NULL,1002,'\0','\0','\0','9650871999','',NULL,NULL),(6,'mukesh.kumar','kumar','mukesh','mukesh.kumar@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',271,NULL,'\0',NULL,1002,'\0','\0','\0','9650871999','',NULL,NULL),(7,'sachin.sahu','sahu','sachin','sachin.sahu@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',271,NULL,'\0',NULL,1002,'\0','\0','\0','9650871999','',NULL,NULL),(8,'neha.yadav','yadav','neha','neha.yadav@tavant.com','Okaya Centre, Tower 1, 5th Floor ','test','noida','up','201309','IN','0123456',NULL,'+91 8197971241',NULL,'2017-05-29','','2017-05-29','1',4,'2017-05-29 00:00:00','',NULL,NULL,'\0','\0','\0','9650871999',NULL,NULL,NULL),(9,'paras.mishra','mishra','paras','paras.mishra@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',5,NULL,'\0',NULL,1002,'\0','\0','\0','9873822199','',NULL,2),(10,'lokesh.bhatt','Bhatt','Lokesh','lokesh.bhatt@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',272,'2017-06-01 12:49:17','','2017-06-01 14:53:15',NULL,'\0','\0','\0','+91 9899671083',NULL,NULL,NULL),(11,'amit.negi','Negi','Amit','amit.negi@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',1,NULL,'\0',NULL,1002,'\0','\0','\0','9873822199','',NULL,NULL),(12,'pooja.awasthi','Awasthi','Pooja','pooja.awasthi@tavant.com','Logix Infotech Park, D-5 Sector 59',NULL,'Noida','UP','201307','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-06-02 17:26:37','','2017-06-02 17:51:59',NULL,'\0','\0','\0','+91 9871756585',NULL,NULL,NULL),(14,'kapil.kumar','kapil','kumar','kapil.kumar@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN','+91 (80) 41190300, 1208',NULL,NULL,NULL,NULL,NULL,NULL,'1',272,'2017-06-06 08:37:58','\0','2017-06-06 08:37:58',NULL,'\0','\0','\0','+91 9035090699',NULL,NULL,NULL),(15,'ashishgumber','Gumber','Ashish','ashish.gumber@tavant.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,'1',1,'2017-06-07 17:58:38','\0','2017-06-07 17:58:38',NULL,'\0','\0','\0',NULL,'$2a$10$9VBu1IzqHwBcfFLxw1crQOwXP/FUmiXcl0sEcNpY1M1kEHOdg9kcm','register',NULL),(16,'shavez.hameed','Hameed','Shavez','shavez.hameed@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN','+91 (120) 4030300, 6084',NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-06-08 12:27:46','','2017-06-08 15:50:22',NULL,'\0','\0','\0','+91 7259449922',NULL,NULL,NULL),(17,'puja.aggarwal','Aggarwal','Puja','puja.aggarwal@tavant.com','Okaya Centre, Tower 1, 5th Floor ',NULL,'Noida','UP','201309','IN','0123456',NULL,'0123456',NULL,NULL,NULL,NULL,'1',1,NULL,'\0',NULL,NULL,'\0','\0','\0',NULL,NULL,NULL,NULL),(405,'ramakrushna.acharya','Acharya','Ramakrushna','ramakrushna.acharya@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Bangalore','Karnataka','201309','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-06-12 15:05:02','\0','2017-06-12 15:05:02',NULL,'\0','\0','\0','+91 9449260289',NULL,NULL,NULL),(406,'divya.sukumaran','Sukumaran','Divya','divya.sukumaran@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN','+91 (80) 49350500, 2249',NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'2017-06-13 17:21:47','','2017-06-13 17:21:47',NULL,'\0','\0','\0','+91 95353 79363',NULL,NULL,NULL),(407,'manish.mehra','Mehra','Manish','manish.mehra@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,'2017-06-13 17:22:37','','2017-06-13 17:22:37',NULL,'\0','\0','\0','+91 99026 80240',NULL,NULL,NULL),(408,'sunny.jha','Kumar Jha','Sunny','sunny.jha@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN','+91 (80) 30431600, 7120',NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'2017-06-13 17:44:39','','2017-06-13 17:44:39',NULL,'\0','\0','\0','+91 8867244873',NULL,NULL,NULL),(409,'ajay.kumar','Kumar','Ajay','ajay.kumar@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'2017-06-13 17:47:42','','2017-06-13 17:47:42',NULL,'\0','\0','\0','+91 8147842100',NULL,NULL,NULL),(410,'sumit.chaturvedi','Chaturvedi','Sumit','sumit.chaturvedi@tavant.com','Logix Infotech Park, D-5 Sector 59',NULL,'Noida','UP','201307','IN','+91 (120) 4030300,408',NULL,NULL,NULL,NULL,NULL,NULL,'1',2,'2017-06-13 17:58:28','\0','2017-06-13 17:59:39',NULL,'\0','\0','\0','+1 201-745-7666/+91 9911940895',NULL,NULL,NULL),(411,'swati.khurana','khurana','swati','swati.khurana@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',2,NULL,'\0',NULL,1002,'\0','\0','\0','9873822199','',NULL,NULL),(412,'samgra.malik','malik','samgra','samgra.malik@tavant.com','test','test','noida','up',NULL,'india','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',1,NULL,'\0',NULL,1002,'\0','\0','\0','9873822199','',NULL,NULL),(413,'charan.karki','karki','charan','charan.karki@tavant.com','test','test','noida','Delhi','2222','India','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',1,'2017-06-13 17:58:28','\0','2017-06-13 17:59:39',1002,'\0','\0','\0','9873822199',NULL,NULL,NULL),(414,'atul.dhemaan','Dhemaan','Atul','atul.dhemaan@tavant.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-06-27 16:42:22','\0',NULL,NULL,'\0','\0','\0',NULL,NULL,NULL,NULL),(415,'chinmay.hegde','Hegde','Chinmay U','chinmay.hegde@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'2017-06-30 10:00:30','\0',NULL,NULL,'\0','\0','\0','+91 9741695496',NULL,NULL,NULL),(416,'ravivarma.pc','PC','Ravivarma','ravivarma.pc@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN','+91 (80) 49350500, 2145',NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'2017-07-04 11:29:18','\0',NULL,NULL,'\0','\0','\0','+91 9952268073',NULL,NULL,NULL),(417,'keshav.raj','raj','keshav','keshav.raj@tavant.com','Okaya Center, Tower 1, 5th floor','Sector 52','Noida','up',NULL,'India','0123456',NULL,NULL,'1002',NULL,NULL,NULL,'1',1,NULL,'\0',NULL,1002,'\0','\0','\0','9650871999','',NULL,NULL),(418,'sachin.tiwari','Tiwari','Sachin','sachin.tiwari@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-07-07 16:42:01','\0',NULL,NULL,'\0','\0','\0','+91 9992253003',NULL,NULL,NULL),(419,'pramod.verma','Kumar Verma','Pramod','pramod.verma@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN','+91 (120) 4030300, 6116',NULL,NULL,NULL,NULL,NULL,NULL,'1',271,'2017-07-12 13:05:54','\0',NULL,NULL,'\0','\0','\0','+91 9953197082',NULL,NULL,NULL),(420,'kriti.mehta','Mehta','Kriti','kriti.mehta@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN','+91 (120) 4030300, 6149',NULL,NULL,NULL,NULL,NULL,NULL,'1',272,'2017-07-12 17:52:20','\0',NULL,NULL,'\0','\0','\0','+91 7838485032',NULL,NULL,NULL),(421,'sakshi.goel','Goel','Sakshi','sakshi.goel@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN','+91 (120) 4030300, 6142',NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-07-13 16:13:38','\0',NULL,NULL,'\0','\0','\0','+91 9990546835',NULL,NULL,NULL),(422,'vijaya.h','H','Vijay','vijaya.h@tavant.com','#12 CSRIE-II, Guava Garden, 5th Block Koramangala',NULL,'Bangalore','Karnataka','560095','IN','+91 (80) 41190300, 1190',NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'2017-07-17 14:54:54','\0',NULL,NULL,'\0','\0','\0','+91 85536 55593',NULL,NULL,NULL),(425,'vijay.kumar','kumar','Vijay','vijay.kumar@tavant.com','Okaya Centre, Tower 1, 5th Floor ',NULL,'Noida','UP','201309','IN','+91 (80) 49350500, 2145',NULL,NULL,'1002',NULL,NULL,NULL,'1',2,NULL,'\0',NULL,1002,'\0','\0','\0','999350500',NULL,NULL,2),(427,'aditya.k','kumar','aditya','aditya.k@tavant.com','okaya',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',1,NULL,'\0',NULL,NULL,'\0','\0','\0','999999999',NULL,NULL,NULL),(428,'akhila.pant','Pant','Akhila','akhila.pant@tavant.com','Okaya Centre, Tower 1, 5th Floor \r\nB-5 Sector 62',NULL,'Noida','UP','201309','IN','+91 (120) 4030300, 6028',NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'2017-10-05 23:10:30','\0',NULL,NULL,'\0','\0','\0','+91 7838867456',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_p678btf3r9yu6u8aevyb4ff0m` (`token`),
  KEY `FK_VERIFY_USER` (`user_id`),
  CONSTRAINT `FK_VERIFY_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-10 11:47:23
