/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.commons.core.domain.v4;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.domain.v4.ODataAnnotation;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.domain.v4.ODataSingleton;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.domain.AbstractODataEntity;

public class ODataEntityImpl extends AbstractODataEntity implements ODataSingleton {

  /**
   * Entity id.
   */
  private URI id;

  private final List<ODataProperty> properties = new ArrayList<ODataProperty>();

  private final List<ODataAnnotation> annotations = new ArrayList<ODataAnnotation>();

  public ODataEntityImpl(final FullQualifiedName typeName) {
    super(typeName);
  }

  @Override
  public URI getId() {
    return id;
  }

  @Override
  public void setId(final URI id) {
    this.id = id;
  }

  @Override
  public ODataProperty getProperty(final String name) {
    return (ODataProperty) super.getProperty(name);
  }

  @Override
  public List<ODataProperty> getProperties() {
    return properties;
  }

  @Override
  public List<ODataAnnotation> getAnnotations() {
    return annotations;
  }

}
