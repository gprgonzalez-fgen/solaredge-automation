int i = ((Integer)globalMap.get("tLoop_1_CURRENT_VALUE")) - 1;
java.util.Date globalStart = TalendDate.parseDate("yyyy-MM-dd", context.startDate);
java.util.Date globalEnd = TalendDate.parseDate("yyyy-MM-dd", context.endDate);

if (context.isHistorical) {
    // Current Chunk Start = Global Start + (i * 28 days)
    java.util.Date currentChunkStart = TalendDate.addDate(globalStart, (i * 28), "dd");
    
    // Current Chunk End = Current Chunk Start + 27 days (for a 28-day span)
    java.util.Date currentChunkEnd = TalendDate.addDate(currentChunkStart, 27, "dd");

    // Boundary Guard: If the 28-day window goes past our context.endDate, cap it!
    if (currentChunkEnd.after(globalEnd)) {
        currentChunkEnd = globalEnd;
    }

    // Format for API (Check timeUnit for detail vs bulk)
//    String format = context.timeUnit.equals("DAY") ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss";
    String format = "yyyy-MM-dd";

    globalMap.put("api_startDate", TalendDate.formatDate(format, currentChunkStart));
    
    // For HH:mm:ss, ensure the end of day is captured
    if (!context.timeUnit.equals("DAY")) {
        // If we need timestamps, make sure the end date is 23:59:59
//        String endStr = TalendDate.formatDate("yyyy-MM-dd", currentChunkEnd) + " 23:59:59";
        String endStr = TalendDate.formatDate("yyyy-MM-dd", currentChunkEnd);

        globalMap.put("api_endDate", endStr);
    } else {
        globalMap.put("api_endDate", TalendDate.formatDate(format, currentChunkEnd));
    }

    System.out.println("Iteration " + (i+1) + ": " + globalMap.get("api_startDate") + " to " + globalMap.get("api_endDate"));

} else {
    // Daily Mode
    globalMap.put("api_startDate", null);
    globalMap.put("api_endDate", null);
}