GUIDELINES!:

* Use StringHandling.TRIM() and REPLACE() logic on all serialNumber and siteId fields to prevent join failures caused by hidden spaces.
* Map metrics (e.g. V, A, W, Wh) to Double in Talend and NUMBER(38,9) in Snowflake to balance performance with accuracy.





Clarifications:

* Timestamp (in snowflake) is of TIMESTAMP\_NTZ or TIMESTAMP\_LTZ

SolarEdge\_SiteList\_Schema

* metadata - some columns have lengths of 0 since there's no entry where they have a value
* publicSettings Snowflake column name, different in tables (DIM\_SOLAREDGE\_SITELIST v SOLAREDGE\_SITE\_DETAILS)
* Site Data: Bulk	/sites/{siteIds}/dataPeriod - does this mean https://monitoringapi.solaredge.com/sites/{all siteids}/dataPeriod
* Consult about connection context.



RAW.SOLAREDGE\_SITE\_ENERGY\_DETAILED

* instead of one primary key SITE\_ID, using composite key (SITE\_ID, DATE, TYPE)



API Corrections

* Site Energy - Time Period: Bulk

  * Example url is incorrect: '.../1,4/energy?timeFrameEnergy?'

    * CorrectURL: /sites/{siteId 1},{siteId 2},…,{siteId n}/timeFrameEnergy
  * Did not mention limits

