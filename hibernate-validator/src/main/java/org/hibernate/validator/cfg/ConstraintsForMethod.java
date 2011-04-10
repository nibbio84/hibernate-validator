/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.validator.cfg;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Via instances of this class method constraints can be configured for a single bean class.
 *
 * @author Kevin Pollet - SERLI - (kevin.pollet@serli.com)
 */
public final class ConstraintsForMethod {
	private static final int EMPTY_PARAMETER_INDEX = -1;

	private final ConstraintMapping mapping;
	private final Class<?> beanClass;
	private final String method;
	private final Class<?>[] parameterTypes;
	private final ElementType elementType;
	private final int index;

	public ConstraintsForMethod(Class<?> beanClass, String method, ConstraintMapping mapping, Class<?>... parameterTypes) {
		this( beanClass, method, METHOD, EMPTY_PARAMETER_INDEX, mapping, parameterTypes );
	}

	private ConstraintsForMethod(Class<?> beanClass, String method, ElementType elementType, int index, ConstraintMapping mapping, Class<?>... parameterTypes) {
		this.mapping = mapping;
		this.beanClass = beanClass;
		this.method = method;
		this.elementType = elementType;
		this.index = index;
		this.parameterTypes = parameterTypes;
	}

	/**
	 * Adds a new constraint.
	 *
	 * @param definition The constraint definition class.
	 *
	 * @return A constraint definition class allowing to specify additional constraint parameters.
	 */
	public <A extends Annotation, T extends ConstraintDef<T, A>> T constraint(Class<T> definition) {
		throw new NotImplementedException();
	}

	/**
	 * Adds a new constraint in a generic way.
	 * <p>
	 * The attributes of the constraint can later on be set by invoking
	 * {@link GenericConstraintDef#addParameter(String, Object)}.
	 * </p>
	 *
	 * @param <A> The annotation type of the constraint to add.
	 * @param definition The constraint to add.
	 *
	 * @return A generic constraint definition class allowing to specify additional constraint parameters.
	 */
	public <A extends Annotation> GenericConstraintDef<A> genericConstraint(Class<A> definition) {
		throw new NotImplementedException();
	}

	/**
	 * Marks the current method parameter or return value as cascadable.
	 *
	 * @return Returns itself for method chaining.
	 */
	public ConstraintsForMethod valid() {
		mapping.addMethodCascadeConfig( new MethodCascadeDef( beanClass, method, index, elementType, parameterTypes ) );
		return this;
	}

	/**
	 * Creates a new {@code ConstraintsForType} in order to define constraints on a new bean type.
	 *
	 * @param type The bean type.
	 *
	 * @return Returns a new {@code ConstraintsForType} instance.
	 */
	public ConstraintsForType type(Class<?> type) {
		return new ConstraintsForType( type, mapping );
	}

	/**
	 * Sets the property for which added constraints apply.
	 *
	 * @param property The property on which to apply the following constraints (Java Bean notation).
	 * @param type The access type (field/property).
	 *
	 * @return Returns itself for method chaining.
	 */
	public ConstraintsForProperty property(String property, ElementType type) {
		return new ConstraintsForProperty( beanClass, property, type, mapping );
	}

	/**
	 * Returns a new {@code ConstraintsForMethod} instance allowing to define
	 * constraints for the given method.
	 *
	 * @param method The method name.
	 * @param parameterTypes The method parameter types.
	 *
	 * @return Returns a new {@code ConstraintsForMethod} instance allowing method chaining.
	 */
	public ConstraintsForMethod method(String method, Class<?>... parameterTypes) {
		return new ConstraintsForMethod( beanClass, method, mapping, parameterTypes );
	}

	/**
	 * Defines constraints on the return value of the current method.
	 *
	 * @return Returns a new {@code ConstraintsForMethod} instance allowing method chaining.
	 */
	public ConstraintsForMethod returnValue() {
		return new ConstraintsForMethod( beanClass, method, METHOD, index, mapping, parameterTypes );
	}

	/**
	 * Changes the parameter for which added constraints apply.
	 *
	 * @param index The parameter index.
	 *
	 * @return Returns a new {@code ConstraintsForMethod} instance allowing method chaining.
	 */
	public ConstraintsForMethod parameter(int index) {
		return new ConstraintsForMethod( beanClass, method, PARAMETER, index, mapping, parameterTypes );
	}
}


