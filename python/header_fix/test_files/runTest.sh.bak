#!/bin/bash
#
# COMPANY NAME: Raytheon Company
# COPYRIGHT: Copyright (c) 2009 Raytheon Company
# UNPUBLISHED WORK
# PROJECT NAME: FCS BCME
# CONTRACT NUMBER: 3EC1721

# DISTRIBUTION STATEMENT D: Distribution authorized to the Department of
# Defense and U.S. DOD contractors administrative or operational use (29
# October 2003). Other requests for this document shall be referred to
# Program Manager Future Combat Systems (Brigade Combat Team),
# ATTN: PM FCS (BCT) Security Office, ATTN:  SFAE-FCS-I/515, 6501 East
# Eleven Mile Road, Warren, MI  48397-5000
#
#
# WARNING - This document contains technical data whose export may be
# restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751,
# et seq) or the Export Administration Act of 1979, as amended (Title 
# 50, U.S.C., App. 2401 et seq). Violations of these export laws are 
# subject to severe criminal penalties. Disseminate in accordance with 
# provisions of DoD Directive 5230.25.
#
#
#This test script is used to manually test the CAM service.
# 
# To use this script, login as user2, execute sclogin, and change to the directory
# containing this file.
#
# The script will open 8 windows, the UAVC, the Onboard UGVC, the Offboard UGVC, the CAM Data Manger, 
# the CAM Service Worker, the CIS, the FTCM mock,the EventSim.
#
# The Script will also run a fathm script needed by CAM to establish control at the begining.
# The script will prompt the tester for actions to be taken.
#
# At the end of test, CTRL-C in each xterm to close.
#
# @author rssa
#   
## 2009-07-28  gkassa  BCME-2169
#------------------------------------------------------------------------------


# The Initialization set up first brings up CIS, UAVC, CAM Datamanager, CAM Service Worker, the UAVC FtcmMock and the
# AISMock GUI. It then run the establishedcontrolAIS fathm script to estbalish control.

exec /fcs/current/test/BCME/bin/testcases/common/controller/Cmn_uavc_setup.sh  &

sleep 30

collect_logs TC0000295

echo
echo  "Test complete, close all windows"
echo  "Be sure to run cleanlogs"
echo

sleep 10


