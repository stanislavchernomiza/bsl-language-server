/*
 * This file is a part of BSL Language Server.
 *
 * Copyright © 2018-2019
 * Alexey Sosnoviy <labotamy@yandex.ru>, Nikita Gryzlov <nixel2007@gmail.com>
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * BSL Language Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * BSL Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BSL Language Server.
 */
package org.github._1c_syntax.intellij.bsl.lsp.server.diagnostics;

import org.eclipse.lsp4j.Diagnostic;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FileInfo {
  private Path path;
  private Diagnostic[] diagnostics;

  public FileInfo() {
    // for DTO
  }

  public FileInfo(Path path, List<Diagnostic> diagnostics) {
    this.path = path;
    this.diagnostics = diagnostics.toArray(new Diagnostic[]{});
  }

  public Path getPath() {
    return path;
  }

  public Diagnostic[] getDiagnostics() {
    return diagnostics;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public void setDiagnostics(Diagnostic[] diagnostics) {
    this.diagnostics = diagnostics;
  }

  @Override
  public String toString() {
    return "FileInfo{" +
      "path=" + path +
      ", diagnostics=" + Arrays.asList(diagnostics) +
      '}';
  }

}
