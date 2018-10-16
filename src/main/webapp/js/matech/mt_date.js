//js时间比较(yyyy-mm-dd hh:mi:ss  eg:"2009-09-21 00:00:00")
function comptime(beginTime, endTime) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');

    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);

    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
      return "endTimeSmall";
    } else if (a > 0) {
        return "endTimeBig";
    } else if (a == 0) {
        return "equal";
    } else {
        return 'exception';
    }
}