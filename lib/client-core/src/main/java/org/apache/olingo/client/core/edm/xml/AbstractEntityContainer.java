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
package org.apache.olingo.client.core.edm.xml;

import java.util.List;

import org.apache.olingo.client.api.edm.xml.CommonFunctionImport;
import org.apache.olingo.client.api.edm.xml.EntityContainer;
import org.apache.olingo.client.api.edm.xml.EntitySet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = EntityContainerDeserializer.class)
public abstract class AbstractEntityContainer extends AbstractEdmItem implements EntityContainer {

  private static final long serialVersionUID = -1114035944715857067L;

  private String name;

  private String _extends;

  private boolean lazyLoadingEnabled;

  private boolean defaultEntityContainer;

  @Override
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getExtends() {
    return _extends;
  }

  public void setExtends(final String _extends) {
    this._extends = _extends;
  }

  @Override
  public boolean isLazyLoadingEnabled() {
    return lazyLoadingEnabled;
  }

  public void setLazyLoadingEnabled(final boolean lazyLoadingEnabled) {
    this.lazyLoadingEnabled = lazyLoadingEnabled;
  }

  @Override
  public boolean isDefaultEntityContainer() {
    return defaultEntityContainer;
  }

  public void setDefaultEntityContainer(final boolean defaultEntityContainer) {
    this.defaultEntityContainer = defaultEntityContainer;
  }

  @Override
  public EntitySet getEntitySet(final String name) {
    return getOneByName(name, getEntitySets());
  }

  /**
   * Gets the first function import with given name.
   *
   * @param name name.
   * @return function import.
   */
  @Override
  public CommonFunctionImport getFunctionImport(final String name) {
    return getOneByName(name, getFunctionImports());
  }

  /**
   * Gets all function imports with given name.
   *
   * @param name name.
   * @return function imports.
   */
  @Override
  public List<? extends CommonFunctionImport> getFunctionImports(final String name) {
    return getAllByName(name, getFunctionImports());
  }
}
