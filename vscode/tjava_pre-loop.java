System.out.println("Job start...");
System.out.println("");

// if isHistorical 
if (context.isHistorical) {
    
    // parse dates
    java.util.Date sDate = TalendDate.parseDate("yyyy-MM-dd", context.startDate);
    java.util.Date eDate = TalendDate.parseDate("yyyy-MM-dd", context.endDate);

    // calculate total days between
    long diffInMillies = Math.abs(eDate.getTime() - sDate.getTime()); // get difference in millies
    long diffInDays = java.util.concurrent.TimeUnit.DAYS.convert(diffInMillies, java.util.concurrent.TimeUnit.MILLISECONDS); // get difference in days
    
    System.out.println("diffInMillies: " + diffInMillies);
    System.out.println("diffInDays: " + diffInDays);

    // calculate how many 28-day chunks (using double to round up)
    int iterations = (int) Math.ceil((double) (diffInDays + 1) / 28);
    
    globalMap.put("totalIterations", iterations);
} 
//else {
//    globalMap.put("totalIterations", 1);
//}
