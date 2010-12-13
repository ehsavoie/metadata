/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;

/**
 * EnvironmentEntriesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnvironmentEntriesMetaDataMerger {
    public static void augment(EnvironmentEntriesMetaData dest, EnvironmentEntriesMetaData augment, EnvironmentEntriesMetaData main, boolean resolveConflicts) {
        for (EnvironmentEntryMetaData envEntry : augment) {
            if (dest.containsKey(envEntry.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(envEntry.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on environment entry named: " + envEntry.getKey());
                } else {
                    EnvironmentEntryMetaDataMerger.augment(dest.get(envEntry.getKey()), envEntry, (main != null) ? main.get(envEntry.getKey()) : null,
                            resolveConflicts);
                }
            } else {
                dest.add(envEntry);
            }
        }
    }

}
