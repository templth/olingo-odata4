/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.v3;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.client.api.v3.ODataClient;
import org.apache.olingo.client.core.AbstractTest;
import org.apache.olingo.client.core.AtomLinksQualifier;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.custommonkey.xmlunit.Diff;
import org.junit.Test;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class AtomTest extends AbstractTest {

  @Override
  protected ODataClient getClient() {
    return v3Client;
  }

  protected ODataFormat getODataPubFormat() {
    return ODataFormat.ATOM;
  }

  protected ODataFormat getODataFormat() {
    return ODataFormat.XML;
  }

  private String cleanup(final String input) throws Exception {
    final TransformerFactory factory = TransformerFactory.newInstance();
    final Source xslt = new StreamSource(getClass().getResourceAsStream("atom_cleanup.xsl"));
    final Transformer transformer = factory.newTransformer(xslt);

    final StringWriter result = new StringWriter();
    transformer.transform(new StreamSource(new ByteArrayInputStream(input.getBytes())), new StreamResult(result));
    return result.toString();
  }

  protected void assertSimilar(final String filename, final String actual) throws Exception {
    final Diff diff = new Diff(cleanup(IOUtils.toString(getClass().getResourceAsStream(filename))), actual);
    diff.overrideElementQualifier(new AtomLinksQualifier());
    assertTrue(diff.similar());
  }

  protected void entitySet(final String filename, final ODataFormat format) throws Exception {
    final StringWriter writer = new StringWriter();
    getClient().getSerializer(format).write(writer, getClient().getDeserializer(format).toEntitySet(
        getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload());

    assertSimilar(filename + "." + getSuffix(format), writer.toString());
  }

  @Test
  public void entitySets() throws Exception {
    entitySet("Customer", getODataPubFormat());
  }

  protected void entity(final String filename, final ODataFormat format) throws Exception {
    final StringWriter writer = new StringWriter();
    getClient().getSerializer(format).write(writer, getClient().getDeserializer(format).toEntity(
        getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload());

    assertSimilar(filename + "." + getSuffix(format), writer.toString());
  }

  @Test
  public void entities() throws Exception {
    entity("AllGeoTypesSet_-5", getODataPubFormat());
    entity("AllGeoTypesSet_-8", getODataPubFormat());
    entity("Car_16", getODataPubFormat());
    entity("ComputerDetail_-10", getODataPubFormat());
    entity("Customer_-10", getODataPubFormat());
    entity("Products_1", getODataPubFormat());
    entity("PersonDetails_0_Person", getODataPubFormat());
    entity("Products_0_Categories", getODataPubFormat());
  }

  protected void property(final String filename, final ODataFormat format) throws Exception {
    final StringWriter writer = new StringWriter();
    getClient().getSerializer(format).write(writer, getClient().getDeserializer(format).toProperty(
        getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload());

    assertSimilar(filename + "." + getSuffix(format), writer.toString());
  }

  @Test
  public void properties() throws Exception {
    property("Products_1_DiscontinuedDate", getODataFormat());
    property("AllGeoTypesSet_-10_GeogLine", getODataFormat());
    property("AllGeoTypesSet_-10_GeogPoint", getODataFormat());
    property("AllGeoTypesSet_-10_Geom", getODataFormat());
    property("AllGeoTypesSet_-3_GeomMultiPolygon", getODataFormat());
    property("AllGeoTypesSet_-5_GeogCollection", getODataFormat());
    property("AllGeoTypesSet_-5_GeogPolygon", getODataFormat());
    property("AllGeoTypesSet_-6_GeomMultiLine", getODataFormat());
    property("AllGeoTypesSet_-7_GeomMultiPoint", getODataFormat());
    property("AllGeoTypesSet_-8_GeomCollection", getODataFormat());
    property("Customer_-10_BackupContactInfo", getODataFormat());
    property("Customer_-10_PrimaryContactInfo", getODataFormat());
    property("MessageAttachment_guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565'_Attachment", getODataFormat());
    property("MessageAttachment_guid'1126a28b-a4af-4bbd-bf0a-2b2c22635565'_AttachmentId", getODataFormat());
    property("Product_-10_ComplexConcurrency_QueriedDateTime", getODataFormat());
    property("Product_-10_Dimensions_Width", getODataFormat());
  }
}
