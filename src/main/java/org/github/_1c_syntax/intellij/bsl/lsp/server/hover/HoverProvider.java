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
package org.github._1c_syntax.intellij.bsl.lsp.server.hover;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.github._1c_syntax.parser.BSLParser.FileContext;
import org.github._1c_syntax.parser.BSLParser.SubNameContext;
import org.github._1c_syntax.parser.BSLParserBaseVisitor;

import java.util.Optional;

public class HoverProvider {

  public static Optional<Hover> getHover(TextDocumentPositionParams position, FileContext fileContext) {

    SubNameFinder finder = new SubNameFinder(position.getPosition());
    finder.visit(fileContext);

    Token subName = finder.getSubName();
    if (subName == null) {
      return Optional.empty();
    }

    Hover hover = new Hover();
    MarkupContent content = new MarkupContent();
    content.setValue(subName.getText());
    hover.setContents(content);
    hover.setRange(
      new Range(
        new Position(subName.getLine() - 1, subName.getCharPositionInLine()),
        new Position(subName.getLine() - 1, subName.getCharPositionInLine() + subName.getText().length())
      )
    );

    return Optional.of(hover);

  }

  private static class SubNameFinder extends BSLParserBaseVisitor<ParseTree> {

    private Token subName;
    private final Position position;

    private SubNameFinder(Position position) {
      this.position = position;
    }

    @Override
    public ParseTree visitSubName(SubNameContext ctx) {

      Token token = ctx.start;
      if (token.getLine() == position.getLine() + 1
        && token.getCharPositionInLine() <= position.getCharacter()
        && position.getCharacter() <= token.getCharPositionInLine() + token.getText().length()) {
        subName = token;
      }
      return ctx;
    }

    Token getSubName() {
      return subName;
    }
  }
}
