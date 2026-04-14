String format = (String)globalMap.get("API_DATE_FORMAT");
int chunkLength = (Integer)globalMap.get("CHUNK_LENGTH");
int minInterval = (Integer)globalMap.get("MIN_INTERVAL");

java.util.Date lastDate = TalendDate.parseDate(format, rowLD.MAX_DATE);

// set globalEnd to yesterday at 23:59:59
java.util.Date yesterdayEnd = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", 
    TalendDate.formatDate("yyyy-MM-dd", new java.util.Date()) + " 00:00:00");
yesterdayEnd = TalendDate.addDate(yesterdayEnd, -1, "ss"); 

// calculate API start (lastDate + 1 ss/dd)
java.util.Date nextStart = format.contains("HH:mm:ss") ? 
                           TalendDate.addDate(lastDate, 1, "ss") : 
                           TalendDate.addDate(lastDate, 1, "dd");

// if up-to-date (Is nextStart + minInterval past yesterdayEnd?)
java.util.Date minRequiredEnd = TalendDate.addDate(nextStart, minInterval, "ss");

// if minRequiredEnd > yesterdayEnd
if (minRequiredEnd.after(yesterdayEnd)) {
    System.out.println("Database is already up to date for " + context.output_table + ". Skipping.");
    globalMap.put("totalIterations", 0);
} else {
    long diffInDays = TalendDate.diffDate(yesterdayEnd, nextStart, "dd");
    if (diffInDays > chunkLength) {
        throw new RuntimeException("Daily gap (" + diffInDays + " days) exceeds limit. Run Historical.");
    }

    globalMap.put("totalIterations", 1);
    globalMap.put("loop_start_datetime", nextStart);
    globalMap.put("loop_end_datetime", yesterdayEnd);
}