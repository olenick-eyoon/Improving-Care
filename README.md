Usage
=====
java -jar {THIS_JAR_NAME.jar} {ARGUMENTS}

### Modes

* Feature execution (testing)
* Report generation

Feature execution arguments
---------------------------
FEATURE_XML_FILENAME

* E.g.: `java -jar avatar.jar feature1.xml`
* E.g.: `java -jar avatar.jar /xmls/all-1.xml`

### Feature file format

Look into [src/test/resources/scenario-specs/full_integration_test.xml](src/test/resources/scenario-specs/full_integration_test.xml)

Report generation arguments
---------------------------
SPEC_CSV_FILENAME EXCEL_FILENAME

* E.g.: `java -jar avatar.jar totals1.csv totals1.xlsx`

### CSV file format

<pre>
{Sheet index: 0|1},{Section title},{System code},{Organization code},{Survey type},{Patient type},{From year: yyyy},{From month: Jan=1},{To year: yyyy},{To month: Jan=1},{Value items: *|ALL}
</pre>

E.g.:

<pre>
0,"Univ. of Colorado - All Orgs","129","_FOC_NULL","HCAHPS","_FOC_NULL","2014/2:2015/1,"Total","DEFAULT"
1,"Eastern Maine - Medical Center","18","106","HCAHPS","_FOC_NULL","2014/2-2015/1","QA"
</pre>
