Clarifications:

* Timestamp (in snowflake) is of TIMESTAMP\_NTZ or TIMESTAMP\_LTZ

SolarEdge\_SiteList\_Schema

* metadata - some columns have lengths of 0 since there's no entry where they have a value
* publicSettings Snowflake column name, different in tables (DIM\_SOLAREDGE\_SITELIST v SOLAREDGE\_SITE\_DETAILS)
* Site Data: Bulk	/sites/{siteIds}/dataPeriod - does this mean https://monitoringapi.solaredge.com/sites/{all siteids}/dataPeriod



RAW.SOLAREDGE\_SITE\_ENERGY\_DETAILED

* instead of one primary key SITE\_ID, using composite key (SITE\_ID, DATE, TYPE)
