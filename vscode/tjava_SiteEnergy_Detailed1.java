int i = ((Integer)globalMap.get("tLoop_1_CURRENT_VALUE")) - 1;
java.util.Date globalStart = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", context.startTime);
java.util.Date globalEnd = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", context.endTime);

if (context.isHistorical) {
    // 1. Calculate the start of this 28-day chunk
    java.util.Date currentChunkStart = TalendDate.addDate(globalStart, (i * 28), "dd");
    
    // 2. Calculate the potential end (28 days later)
    // We use addDate and then set the time to the end of that day ONLY if it's not the final boundary
    java.util.Date currentChunkEnd = TalendDate.addDate(currentChunkStart, 27, "dd");
    
    // Convert to a string momentarily to set it to the very end of that 28th day
    String endOfDayStr = TalendDate.formatDate("yyyy-MM-dd", currentChunkEnd) + " 23:59:59";
    currentChunkEnd = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", endOfDayStr);

    // 3. Boundary Guard: If the 28-day window exceeds context.endTime, use context.endTime EXACTLY
    if (currentChunkEnd.after(globalEnd)) {
        currentChunkEnd = globalEnd;
    }

    String format = "yyyy-MM-dd HH:mm:ss";

    // Set Global Variables
    globalMap.put("api_startTime", TalendDate.formatDate(format, (i == 0 ? globalStart : currentChunkStart))); // ensure the very first request starts exactly at the time that's specified in the context.startTime
    globalMap.put("api_endTime", TalendDate.formatDate(format, currentChunkEnd));

    System.out.println("Iteration " + (i+1) + ": " + globalMap.get("api_startTime") + " to " + globalMap.get("api_endTime"));
} 
else {
    // Daily Mode
    globalMap.put("api_startTime", "");
    globalMap.put("api_endTime", "");
}