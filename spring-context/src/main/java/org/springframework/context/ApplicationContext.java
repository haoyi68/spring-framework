/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;

/**
 * 从代码层次来看：Spring容器就是一个实现了ApplicationContext接口的对象，
 *
 * 从功能上来看： Spring容器是 Spring 框架的核心，是用来管理对象的。容器将创建对象，把它们连接在一起，配置它们，并管理他们的整个生命周期从创建到销毁
 */

/**
 * Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 *
 * 提供应用配置功能的中心接口。
 * 在应用程序运行时，它是只读的，但是如果实现支持，则可以重新加载。
 *
 * <p>An ApplicationContext provides:
 *  一个应用容器（应用上下文）提供一下功能
 * <ul>
 * <li>Bean factory methods for accessing application components.
 *    用于访问应用程序组件（实例）的实例工厂方法。
 * Inherited from {@link org.springframework.beans.factory.ListableBeanFactory}.
 * <li>The ability to load file resources in a generic fashion.
 *    以通用方式加载文件资源的能力
 * Inherited from the {@link org.springframework.core.io.ResourceLoader} interface.
 * <li>The ability to publish events to registered listeners.
 *    可以将事件发布给注册监听的能力
 * Inherited from the {@link ApplicationEventPublisher} interface.
 * <li>The ability to resolve messages, supporting internationalization.
 *     处理信息，支持国际化的能力
 * Inherited from the {@link MessageSource} interface.
 * <li>Inheritance from a parent context. Definitions in a descendant context
 * will always take priority. This means, for example, that a single parent
 * context can be used by an entire web application, while each servlet has
 * its own child context that is independent of that of any other servlet.
 *  从父类（接口）继承应用容器（应用上下文）。 子类（接口）中的定义将始终优先。
 *  例如，这意味着单亲上下文可以由整个Web应用程序使用，而每个Servlet都有其自己的子上下文，该子上下文独立于任何其他servlet的子上下文。
 * </ul>
 *
 * <p>In addition to standard {@link org.springframework.beans.factory.BeanFactory}
 * lifecycle capabilities, ApplicationContext implementations detect and invoke
 * {@link ApplicationContextAware} beans as well as {@link ResourceLoaderAware},
 * {@link ApplicationEventPublisherAware} and {@link MessageSourceAware} beans.
 *
 * 此外一个标准bean的整个生命周期中，容器会检测和调用实现对应*Aware接口的实例。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see ConfigurableApplicationContext
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.core.io.ResourceLoader
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
		MessageSource, ApplicationEventPublisher, ResourcePatternResolver {

	/**
	 * Return the unique id of this application context.
	 * 返回应用容器的唯一id
	 * @return the unique id of the context, or {@code null} if none
	 */
	@Nullable
	String getId();

	/**
	 * Return a name for the deployed application that this context belongs to.
	 * 返回此上下文所属的已部署应用程序的名称。
	 * @return a name for the deployed application, or the empty String by default
	 */
	String getApplicationName();

	/**
	 * Return a friendly name for this context.
	 * 返回一个友好的上下文名称
	 * @return a display name for this context (never {@code null})
	 */
	String getDisplayName();

	/**
	 * Return the timestamp when this context was first loaded.
	 * 返回上下文被首次加载的时间
	 * @return the timestamp (ms) when this context was first loaded
	 */
	long getStartupDate();

	/**
	 * Return the parent context, or {@code null} if there is no parent
	 * and this is the root of the context hierarchy.
	 * 返回对应的父容器，如果没有父容器则自己为上下文层次结构的根
	 * @return the parent context, or {@code null} if there is no parent
	 */
	@Nullable
	ApplicationContext getParent();

	/**
	 * Expose AutowireCapableBeanFactory functionality for this context.
	 * 针对此上下文开放AutowireCapableBeanFactory功能
	 * <p>This is not typically used by application code, except for the purpose of
	 * initializing bean instances that live outside of the application context,
	 * applying the Spring bean lifecycle (fully or partly) to them.
	 * 除了初始化驻留在应用程序上下文之外的bean实例，将Spring bean生命周期（全部或部分）应用于它们的目的之外，
	 * 应用程序代码通常不使用此代码。
	 * <p>Alternatively, the internal BeanFactory exposed by the
	 * {@link ConfigurableApplicationContext} interface offers access to the
	 * {@link AutowireCapableBeanFactory} interface too. The present method mainly
	 * serves as a convenient, specific facility on the ApplicationContext interface.
	 *
	 * <p><b>NOTE: As of 4.2, this method will consistently throw IllegalStateException
	 * after the application context has been closed.</b> In current Spring Framework
	 * versions, only refreshable application contexts behave that way; as of 4.2,
	 * all application context implementations will be required to comply.
	 * @return the AutowireCapableBeanFactory for this context
	 * @throws IllegalStateException if the context does not support the
	 * {@link AutowireCapableBeanFactory} interface, or does not hold an
	 * autowire-capable bean factory yet (e.g. if {@code refresh()} has
	 * never been called), or if the context has been closed already
	 * @see ConfigurableApplicationContext#refresh()
	 * @see ConfigurableApplicationContext#getBeanFactory()
	 */
	AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

}
