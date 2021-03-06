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
package org.apache.olingo.client.core.communication.request.cud;

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.olingo.client.api.CommonODataClient;
import org.apache.olingo.client.api.communication.request.cud.ODataPropertyUpdateRequest;
import org.apache.olingo.client.api.communication.response.ODataPropertyUpdateResponse;
import org.apache.olingo.client.core.communication.request.AbstractODataBasicRequest;
import org.apache.olingo.client.core.communication.response.AbstractODataResponse;
import org.apache.olingo.client.core.uri.URIUtils;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ResWrap;
import org.apache.olingo.commons.api.domain.CommonODataProperty;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.commons.api.serialization.ODataDeserializerException;
import org.apache.olingo.commons.api.serialization.ODataSerializerException;

/**
 * This class implements an OData update entity property request.
 */
public class ODataPropertyUpdateRequestImpl extends AbstractODataBasicRequest<ODataPropertyUpdateResponse>
        implements ODataPropertyUpdateRequest {

  /**
   * Value to be created.
   */
  private final CommonODataProperty property;

  /**
   * Constructor.
   *
   * @param odataClient client instance getting this request
   * @param method request method.
   * @param targetURI entity set or entity or entity property URI.
   * @param property value to be created.
   */
  ODataPropertyUpdateRequestImpl(final CommonODataClient<?> odataClient,
          final HttpMethod method, final URI targetURI, final CommonODataProperty property) {

    super(odataClient, method, targetURI);
    // set request body
    this.property = property;
  }

  @Override
  public ODataFormat getDefaultFormat() {
    return odataClient.getConfiguration().getDefaultFormat();
  }

  @Override
  public ODataPropertyUpdateResponse execute() {
    final InputStream input = getPayload();
    ((HttpEntityEnclosingRequestBase) request).setEntity(URIUtils.buildInputStreamEntity(odataClient, input));

    try {
      return new ODataPropertyUpdateResponseImpl(odataClient, httpClient, doExecute());
    } finally {
      IOUtils.closeQuietly(input);
    }
  }

  @Override
  protected InputStream getPayload() {
    try {
      return odataClient.getWriter().writeProperty(property, ODataFormat.fromString(getContentType()));
    } catch (final ODataSerializerException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Response class about an ODataPropertyUpdateRequest.
   */
  private class ODataPropertyUpdateResponseImpl extends AbstractODataResponse implements ODataPropertyUpdateResponse {

    private CommonODataProperty property = null;

    private ODataPropertyUpdateResponseImpl(final CommonODataClient<?> odataClient, final HttpClient httpClient,
            final HttpResponse res) {

      super(odataClient, httpClient, res);
    }

    @Override
    public CommonODataProperty getBody() {
      if (property == null) {
        try {
          final ResWrap<Property> resource = odataClient.getDeserializer(ODataFormat.fromString(getAccept())).
                  toProperty(getRawResponse());

          property = odataClient.getBinder().getODataProperty(resource);
        } catch (final ODataDeserializerException e) {
          throw new IllegalArgumentException(e);
        } finally {
          this.close();
        }
      }
      return property;
    }
  }
}
