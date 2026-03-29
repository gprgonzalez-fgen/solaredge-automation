int i = ((Integer)globalMap.get("tLoop_1_CURRENT_VALUE")) - 1;
java.util.Date globalStart = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", context.startTime);
java.util.Date globalEnd = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", context.endTime);

if (context.isHistorical) {
    // 1. Calculate the start of this 28-day chunk
    java.util.Date currentChunkStart = TalendDate.addDate(globalStart, (i * 28), "dd");
    
    // 2. Calculate the end (Jump 28 days total from start)
    // CHANGE: We calculate the start of the NEXT chunk, then subtract 1 second.
    // This ensures Iteration 1 ends exactly where Iteration 2 begins (No Gaps).
    java.util.Date nextChunkStart = TalendDate.addDate(currentChunkStart, 28, "dd");
    java.util.Date currentChunkEnd = TalendDate.addDate(nextChunkStart, -1, "ss"); 

    // 3. Boundary Guard: If the 28-day window exceeds context.endTime, cap it.
    if (currentChunkEnd.after(globalEnd)) {
        currentChunkEnd = globalEnd;
    }

    String format = "yyyy-MM-dd HH:mm:ss";

    // Set Global Variables
    // Use globalStart for i=0 to keep the specific starting hour (e.g., 11:00:00)
    globalMap.put("api_startTime", TalendDate.formatDate(format, (i == 0 ? globalStart : currentChunkStart)));
    globalMap.put("api_endTime", TalendDate.formatDate(format, currentChunkEnd));

    System.out.println("Iteration " + (i+1) + ": " + (String)globalMap.get("api_startTime") + " to " + (String)globalMap.get("api_endTime"));
} 
else {
    globalMap.put("api_startTime", "");
    globalMap.put("api_endTime", "");
}