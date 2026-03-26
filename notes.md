GUIDELINES!:

* Use StringHandling.TRIM() and REPLACE() logic on all serialNumber and siteId fields to prevent join failures caused by hidden spaces.
* Map metrics (e.g. V, A, W, Wh) to Double in Talend and NUMBER(38,9) in Snowflake to balance performance with accuracy.





Considerations:

* Example for Components List, since it's not bulk, per request only returns count: 2-4 but target
* How to handle process log for isHistorical/Looping since multiple request using trestclient, so multiple API\_STATUS\_CODE etc.

  * do i only log the last iteration? or do i log per iteration? 
  * for columns like API\_STATUS\_CODE, do i update it per request?
* not all api endpoint requests return a "count" field. therefore, should i keep the count to 0?



Clarifications:

* Timestamp (in snowflake) is of TIMESTAMP\_NTZ or TIMESTAMP\_LTZ

SolarEdge\_SiteList\_Schema

* metadata - some columns have lengths of 0 since there's no entry where they have a value
* publicSettings Snowflake column name, different in tables (DIM\_SOLAREDGE\_SITELIST v SOLAREDGE\_SITE\_DETAILS)
* Site Data: Bulk	/sites/{siteIds}/dataPeriod - does this mean https://monitoringapi.solaredge.com/sites/{all siteids}/dataPeriod
* Consult about connection context.



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



Prejob component is useless on this project. Snowflake components are initializing parallel to prejob, causing errors



Talend Common Bugs:

* Snowflake components might run or initialize before or parallel to prejob -- Prejob component is useless on this project. Snowflake components are initializing parallel to prejob, causing errors
* tRestClient component causing context variable values to not update when changed -- https://community.qlik.com/t5/Talend-Studio/context-variables-are-not-populated-correctly/td-p/2362722
* When updating repository context groups/metadata, it might not propagate properly to jobs that are currently using it



Process log

* how to handle process log API\_STATUS\_CODE

  * (String)globalMap.get("tRESTClient\_1\_ERROR\_MESSAGE") - contains, for example, HTTP 401 Unauthorized/HTTP 400 Bad Request
  * globalMap.put("API\_STATUS\_CODE", rowASC.statusCode); - from response schema
  * even if globalMap.get("tRESTClient\_1\_ERROR\_MESSAGE") is present
  * Questions: 

    * Should i kill the job as soon as there's an error on trestclient?
    * Since parallel execution is enabled, there's a chance na one of those executions has an error



