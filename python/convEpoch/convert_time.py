#!/bin/env python

import os, sys, time, string, re, exceptions, datetime

def usage(Exception):
    """
        Usage: [python] ./convert_time.py <type> <value>
        where <type> is one of the following: '-human', '-seconds', '-milli', '-micro', or '-microsnow'
              <value> is a value that corresponds to the <type>

        Examples:
        './convert_time.py -human "Thu Jun 28 14:17:15 2001"' returns seconds since epoch
        './convert_time.py -seconds 1261451219'               returns a string like "2009-12-22 04:32:50"
        './convert_time.py -milli 1261451219.1234'            returns a string like "2009-12-22 04:32:50.1234"
        './convert_time.py -micro 1261451219.123456'          returns a string like "2009-12-22 04:32:50.123456"
        './convert_time.py -microsnow'                        returns a string like "2009-12-22 04:32:50.123456"
                                                                                    "secondsSinceEpoch      -> 1261457492"
                                                                                    "microSecondsSinceEpoch -> 1261457492123456"
    """
    print "\n\tERROR: %s. See usage below." % (e)
    print usage.__doc__

def to_epoch(from_human):
    """
    Returns human readable time specified in the format below to milliseconds since epoch.
    val - is the default format like "%a %b %d %H:%M:%S %Y", where each specifier is defined below. 
    (e.g. - "Thu Jun 28 14:17:15 2001")

    %a Locale's abbreviated weekday name.  
    %b Locale's abbreviated month name.  
    %d Day of the month as a decimal number [01,31].  
    %H Hour (24-hour clock) as a decimal number [00,23].  
    %M Minute as a decimal number [00,59].  
    %S Second as a decimal number [00,61]. (2) 
    %Y Year with century as a decimal number.  

    """
    toStructTime = time.strptime(from_human)
    toEpochTime = time.mktime(toStructTime)
    print toEpochTime
    return toEpochTime

def to_human_from_seconds(from_epoch_seconds):
    """
    Returns seconds since epoch into human readable time.
    """
    patt = re.compile(r'(\d{10})')
    val = patt.findall(from_epoch_seconds)[0]
    val = float(from_epoch_seconds)
    toStructTime = time.gmtime(val)
    toStringTime = time.strftime("%Y-%m-%d %H:%M:%S",toStructTime)
    print toStringTime

def to_human_from_milliseconds(from_epoch_milliseconds):
    """
    Returns milliseconds since epoch into human readable time.
    """
    patt = re.compile(r'(\d{10}?.\d{4})')
    val = patt.findall(from_epoch_milliseconds)[0]
    seconds = val.split('.')[0]
    milli = val.split('.')[1]
    val = float(seconds)
    toStructTime = time.gmtime(val)
    milli = milli + "00"
    d = datetime.datetime(toStructTime.tm_year, toStructTime.tm_mon, toStructTime.tm_mday, toStructTime.tm_hour, toStructTime.tm_min, toStructTime.tm_sec, int(milli), None)
    print d

def to_human_from_microseconds(from_epoch_microseconds):
    """
    Returns microseconds since epoch into human readable time.
    """
    patt = re.compile(r'(\d{10}?.\d{6})')
    val = patt.findall(from_epoch_microseconds)[0]
    seconds = val.split('.')[0]
    micro = val.split('.')[1]
    val = float(seconds)
    toStructTime = time.gmtime(val)
    d = datetime.datetime(toStructTime.tm_year, toStructTime.tm_mon, toStructTime.tm_mday, toStructTime.tm_hour, toStructTime.tm_min, toStructTime.tm_sec, int(micro), None)
    print d

def to_micros_now():
    """
    Returns seconds since epoch.
    """
    d = datetime.datetime.now()
    dStr = str(d)
    print dStr
    patt = re.compile('(\d\d\d\d-\d\d-\d\d\s\d\d:\d\d:\d\d).(\d\d\d\d\d\d)')
    # capture DTG and microseconds
    # val[0] contains "YYYY-MM-DD HH:MM:SS"
    # val[1] contains the microseconds
    val = patt.findall(dStr)[0]
    microSeconds = val[1]
  
    t = time.mktime(d.timetuple())
    tStr = str(t)
    patt1 = re.compile('(\d{10})')
    secondsSinceEpoch = patt1.findall(tStr)[0]
    print 'secondsSinceEpoch      -> %s' % (secondsSinceEpoch)
    print 'microSecondsSinceEpoch -> %s%s' % (secondsSinceEpoch,microSeconds) 
    

if __name__ == "__main__":

    try:

        if sys.argv[1] == '-human':
            to_string = ''.join(sys.argv[2:])
            to_epoch(to_string)

        elif sys.argv[1] == '-seconds':
            to_human_from_seconds(sys.argv[2])

        elif sys.argv[1] == '-milli':
            to_human_from_milliseconds(sys.argv[2])

        elif sys.argv[1] == '-micro':
            to_human_from_microseconds(sys.argv[2])

        elif sys.argv[1] == '-microsnow':
            to_micros_now()

        else:
            e = Exception("Invalid option")
            usage(e)

    except Exception, e:
            usage(e)



