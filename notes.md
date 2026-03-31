GUIDELINES!:

* Use StringHandling.TRIM() and REPLACE() logic on all serialNumber and siteId fields to prevent join failures caused by hidden spaces.
* Map metrics (e.g. V, A, W, Wh) to Double in Talend and NUMBER(38,9) in Snowflake to balance performance with accuracy.





Considerations:

* ~~Example for Components List, since it's not bulk, per request only returns count: 2-4 but target~~
* ~~How to handle process log for isHistorical/Looping since multiple request using trestclient, so multiple API\_STATUS\_CODE etc.~~

  * ~~do i only log the last iteration? or do i log per iteration?~~
  * ~~for columns like API\_STATUS\_CODE, do i update it per request?~~
* ~~not all api endpoint requests return a "count" field. therefore, should i keep the count to 0?~~
* For the daily mode, what's the maximum difference from today's date? 28 days?
* ~~For snowflake tables that doesn't have a "Date" column (date value from api respone, not SNOWFLAKE\_DATE\_CREATED), what should i use to get the "max date" for daily mode?~~
* For non-looping api endpoints, do i do a daily mode? or manually siya lalagyan ng date through contexts (startDate, endDate) lagi? like SiteEnergy\_TimePeriod
* ~~For Inventory API endpoint: what should be my keys? SITE\_ID and EQUIPMENT CATEGORY isn't enough:~~ dont assign any keys
* For Meters Data: should i improve logging to say "NO DATA RETRIEVED" (instead of "FAILED") for status when SOURCE\_ROW\_COUNT and TARGET\_ROW\_COUNT is equal to 0?



Clarifications:

* ~~Timestamp (in snowflake) is of TIMESTAMP\_NTZ or TIMESTAMP\_LTZ:~~ TIMESTAMP\_NTZ
* publicSettings Snowflake column name, different in tables (DIM\_SOLAREDGE\_SITELIST v SOLAREDGE\_SITE\_DETAILS)
* For Site Energy – Time Period, request timeframe limits is: "1 Year max (Daily)", meaning?
* For Site Overview: Bulk, do i use SITE\_ID and LAST\_UPDATE\_TIME as primary\_key? or stay with SITE\_ID



API Corrections

* Site Energy - Time Period: Bulk

  * Example url is incorrect: '.../1,4/energy?timeFrameEnergy?'

    * CorrectURL: /sites/{siteId 1},{siteId 2},…,{siteId n}/timeFrameEnergy
  * Did not mention limits
* Site Power

  * Example url is incorrect: '...startTime=2013-05-5%2011:00:00\&endTime=2013-05-05%2013:00:00...'

    * Correct: '...startTime=2013-05-05%2011:00:00\&endTime=2013-05-06%2013:00:00...'
* Site Energy: Bulk

  * startDate 2013-05-01 and endDate 2013-05-30 works but not 2014-02-25 to 2014-03-26 even if they have the same time/day difference





Talend Common Bugs:

* Snowflake components might run or initialize before or parallel to prejob -- Prejob component is useless on this project. Snowflake components are initializing parallel to prejob, causing errors
* tRestClient component causing context variable values to not update when changed -- https://community.qlik.com/t5/Talend-Studio/context-variables-are-not-populated-correctly/td-p/2362722
* When updating repository context groups/metadata, it might not propagate properly to jobs that are currently using it



Process log

* how to handle process log API\_STATUS\_CODE

  * (String)globalMap.get("tRESTClient\_1\_ERROR\_MESSAGE") - contains, for example, HTTP 401 Unauthorized/HTTP 400 Bad Request
  * globalMap.put("API\_STATUS\_CODE", rowASC.statusCode); - from response schema
  * even if globalMap.get("tRESTClient\_1\_ERROR\_MESSAGE") is present
* REMEMBER:

  * Set tRestClient to NOT die on error
  * Rename row to rowED before tMap
  * Check tRestClient query parameters (specifically for startDate/startTime and endDate/endTime to use (String)globalMap.get("api\_startTime")/(String)globalMap.get("api\_endTime")
  * Add: when both SOURCE\_ROW\_COUNT and TARGET\_ROW\_COUNT is 0, put unsuccessful
  * Add: consider using (String)globalMap.get("api\_startTime")/(String)globalMap.get("api\_endTime") instead of context.startDate/context.endDate for non-timeseries jobs
  * Improve DiffInDays for same day and/or same day different time



Revisions

* merge tExtractJSONField for SOURCE\_TARGET\_COUNT, with main row line
* revise Inventory job -- make tExtractJSONField components in a singular flow
* Site Energy: Time Period: Daily and isHistorical

