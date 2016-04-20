/*
 * SonarQube Scanner for Maven
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.scanner.maven.bootstrap;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.project.MavenProject;

/**
 * An utility class to manipulate Maven concepts
 *
 * @since 1.10
 */
public final class MavenUtils {

  private static final String MAVEN_COMPILER_PLUGIN = "maven-compiler-plugin";

  public static final String GROUP_ID_APACHE_MAVEN = "org.apache.maven.plugins";

  public static final String GROUP_ID_CODEHAUS_MOJO = "org.codehaus.mojo";

  private MavenUtils() {
    // utility class with only static methods
  }

  /**
   * Returns the version of Java used by the maven compiler plugin
   *
   * @param pom the project pom
   * @return the java version
   */
  @CheckForNull
  public static String getJavaVersion(MavenProject pom) {
    String defaultValue = pom.getProperties().getProperty("maven.compiler.target");
    return getPluginSetting(pom, GROUP_ID_APACHE_MAVEN, MAVEN_COMPILER_PLUGIN, "target", defaultValue);
  }

  @CheckForNull
  public static String getJavaSourceVersion(MavenProject pom) {
    String defaultValue = pom.getProperties().getProperty("maven.compiler.source");
    return getPluginSetting(pom, GROUP_ID_APACHE_MAVEN, MAVEN_COMPILER_PLUGIN, "source", defaultValue);
  }

  /**
   * @param pom the project pom
   * @return source encoding
   */
  @CheckForNull
  public static String getSourceEncoding(MavenProject pom) {
    return pom.getProperties().getProperty("project.build.sourceEncoding");
  }

  /**
   * Search for a configuration setting of an other plugin for a configuration setting.
   *
   * @param project the current maven project to get the configuration from.
   * @param groupId the group id of the plugin to search for
   * @param artifactId the artifact id of the plugin to search for
   * @param optionName the option to get from the configuration
   * @param defaultValue the default value if the configuration was not found
   * @return the value of the option configured in the plugin configuration
   */
  public static String getPluginSetting(MavenProject project, String groupId, String artifactId, String optionName, @Nullable String defaultValue) {
    MavenPlugin plugin = MavenPlugin.getPlugin(project, groupId, artifactId);
    if (plugin != null) {
      return StringUtils.defaultIfEmpty(plugin.getParameter(optionName), defaultValue);
    }
    return defaultValue;
  }

}
