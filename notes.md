Clarifications:

* Timestamp (in snowflake) is of TIMESTAMP\_NTZ or TIMESTAMP\_LTZ

SolarEdge\_SiteList\_Schema

* metadata - some columns have lengths of 0 since there's no entry where they have a value
* publicSettings Snowflake column name, different in tables (DIM\_SOLAREDGE\_SITELIST v SOLAREDGE\_SITE\_DETAILS)





**ADD**

Source column: uris.PUBLIC\_URL

Snowflake column: URIS\_PUBLIC\_URL

Data type: String

Format:

Sample Data from Source:

Remarks:



**UPDATE**

Source column: publicsettings.isPublic

Snowflake column: PUBLIC\_SETTINGS\_IS\_PUBLIC

Sample Data from Source: false



**ADD**

Source column: publicsettings.name

Snowflake column: PUBLIC\_SETTINGS\_NAME

Data type:

Format:

Sample Data from Source:

Remarks:

