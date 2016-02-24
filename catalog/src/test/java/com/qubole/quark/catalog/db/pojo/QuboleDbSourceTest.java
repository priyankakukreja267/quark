/*
 * Copyright (c) 2015. Qubole Inc
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.qubole.quark.catalog.db.pojo;

import com.qubole.quark.catalog.db.dao.QuboleDbSourceDAO;
import com.qubole.quark.catalog.db.encryption.MysqlAES;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by adeshr on 2/17/16.
 */
public class QuboleDbSourceTest extends DbUtility {

  private static final String dbSchemaUrl = "jdbc:h2:mem:QuboleDbSourceTest;DB_CLOSE_DELAY=-1";
  private static QuboleDbSourceDAO quboleDbSourceDAO;
  @BeforeClass
  public static void setUpClass() throws ClassNotFoundException, SQLException,
      IOException, URISyntaxException {

    setUpDb(dbSchemaUrl, "sa", "", "tpcds.sql");

    MysqlAES mysqlAES = MysqlAES.getInstance();
    mysqlAES.setKey("easy");

    DBI dbi = new DBI(dbSchemaUrl, "sa", "");
    quboleDbSourceDAO = dbi.onDemand(QuboleDbSourceDAO.class);
  }

  @Test
  public void testGet() {
    List<QuboleDbSource> quboleDbSources = quboleDbSourceDAO.findByDSSetId(1);
    assertThat(quboleDbSources.size(), equalTo(1));
  }
}
