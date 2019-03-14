CREATE DATABASE  IF NOT EXISTS busnet;
USE busnet;

--
-- Table structure for table bus routes
--

DROP TABLE IF EXISTS bus_route;
CREATE TABLE bus_route(
  number int(11) NOT NULL,
  price double UNSIGNED NOT NULL,
  is_roundabout tinyint(1) NOT NULL,
  time_interval int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table bus stops
--

DROP TABLE IF EXISTS bus_stop;
CREATE TABLE bus_stop (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  parking_space int(11) UNSIGNED NOT NULL,
  x_coord double UNSIGNED NOT NULL,
  y_coord double UNSIGNED NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table routes and stops (M2M)
--

DROP TABLE IF EXISTS bus_route_stop;
CREATE TABLE bus_route_stop (
  stop_id int(11) NOT NULL,
  route_number int(11) NOT NULL,
  PRIMARY KEY (stop_id, route_number),
  KEY bus_route_stop_stopidx (stop_id),
  CONSTRAINT fk_bus_route_stop_stopid FOREIGN KEY (stop_id) REFERENCES bus_stop (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_bus_route_stop_routenum FOREIGN KEY (route_number) REFERENCES bus_route (number) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table statistics
--

DROP TABLE IF EXISTS statistics;
CREATE TABLE statistics (
  id bigint NOT NULL AUTO_INCREMENT,
  start_time datetime,
  end_time datetime,
  time_simulation int(11) UNSIGNED NOT NULL,
  load_percentage double UNSIGNED NOT NULL,
  routes_count int(11) UNSIGNED NOT NULL,
  bus_count int(11) UNSIGNED NOT NULL,
  passengers_count int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table busLog
--

DROP TABLE IF EXISTS bus_log;
CREATE TABLE bus_log (
  id bigint NOT NULL AUTO_INCREMENT,
  time_moment datetime,
  bus_stop_id int(11) NOT NULL,	
  route_num int(11) NOT NULL,
  bus_id int(11) UNSIGNED NOT NULL,
  passengers_count_entry int(11) UNSIGNED NOT NULL,
  passengers_count_exit int(11) UNSIGNED NOT NULL,
  passengers_count_in int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_bus_stop_id FOREIGN KEY (bus_stop_id) REFERENCES bus_stop (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_bus_route_num FOREIGN KEY (route_num) REFERENCES bus_route (number) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

