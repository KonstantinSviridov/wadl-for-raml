/*
 * Copyright (c) MuleSoft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.raml.parser.builder;

import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.parser.resolver.ITransformHandler;

public class QuestionedActionTypeHandler implements ITransformHandler{

	public Object handle(Object value,Object parent){
		if (value.toString().endsWith("?")){
		Action c=(Action) parent;
		c.setQuestioned(true);
		return ActionType.valueOf(value.toString().substring(0,value.toString().length()-1).toUpperCase());
		}
		return value;
	}
}
