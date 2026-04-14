//--- MANUAL SETTINGS PER JOB ---
String format = "yyyy-MM-dd HH:mm:ss";
int chunkLength = 28; 
int minIntervalSeconds = 1;
//-------------------------------

System.out.println("Initializing Job: " + jobName);
globalMap.put("API_DATE_FORMAT", format);
globalMap.put("CHUNK_LENGTH", chunkLength);
globalMap.put("MIN_INTERVAL", minIntervalSeconds);
globalMap.put("SOURCE_ROW_COUNT", 0);
globalMap.put("TARGET_ROW_COUNT", 0);

if (context.isHistorical) {
 java.util.Date sDate = TalendDate.parseDate(format, context.startTime);
 java.util.Date eDate = TalendDate.parseDate(format, context.endTime);

 // VALIDATION: if start >= end, throw runtime exception
 if (!sDate.before(eDate)) {
     throw new RuntimeException("CONFIG ERROR: Start Date must be before End Date.");
 }

 // calculate difference in days
 long diffInDays = java.util.concurrent.TimeUnit.DAYS.convert(Math.abs(eDate.getTime() - sDate.getTime()), java.util.concurrent.TimeUnit.MILLISECONDS);

 // calculate number of iterations
 int iterations = (int) Math.ceil((double) (diffInDays + 1) / chunkLength);
 
 globalMap.put("totalIterations", iterations);
 globalMap.put("loop_start_datetime", sDate);
 globalMap.put("loop_end_datetime", eDate);
} else {
 globalMap.put("totalIterations", 1); 
}