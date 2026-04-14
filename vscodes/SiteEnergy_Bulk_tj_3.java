int i = ((Integer)globalMap.get("tLoop_1_CURRENT_VALUE")) - 1;
int chunkLength = (Integer)globalMap.get("CHUNK_LENGTH");
int minInterval = (Integer)globalMap.get("MIN_INTERVAL");
String format = (String)globalMap.get("API_DATE_FORMAT");

java.util.Date globalStart = (java.util.Date)globalMap.get("loop_start_datetime");
java.util.Date globalEnd = (java.util.Date)globalMap.get("loop_end_datetime");

java.util.Date currentChunkStart = TalendDate.addDate(globalStart, (i * chunkLength), "dd");

if (currentChunkStart.after(globalEnd)) {
	// force 0 loop
    globalMap.put("api_startDate", TalendDate.formatDate(format, globalEnd));
    globalMap.put("api_endDate", TalendDate.formatDate(format, globalEnd));
} else {
    java.util.Date nextStart = TalendDate.addDate(currentChunkStart, chunkLength, "dd");
    java.util.Date currentChunkEnd = TalendDate.addDate(nextStart, -1, "ss");

    // boundary & min interval guard
    if (currentChunkEnd.after(globalEnd)) {
        currentChunkEnd = globalEnd;
    }
    
    // ensure start and end meet the API minimum gap requirement
    long actualGap = (currentChunkEnd.getTime() - currentChunkStart.getTime()) / 1000;
    if (actualGap < minInterval) {
        currentChunkEnd = TalendDate.addDate(currentChunkStart, minInterval, "ss");
    }

    globalMap.put("api_startDate", TalendDate.formatDate(format, currentChunkStart));
    globalMap.put("api_endDate", TalendDate.formatDate(format, currentChunkEnd));

    System.out.println("Iteration " + (i+1) + " | " + globalMap.get("api_startDate") + " to " + globalMap.get("api_endDate"));
}