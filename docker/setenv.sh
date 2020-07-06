#!/usr/bin/env bash

DB_OPTS="-DICE_DB_URL=${ICE_DB_URL:-jdbc:postgresql://postgres/ice}"
DB_OPTS="${DB_OPTS} -DICE_DB_USER=${ICE_DB_USER:-iceuser}"
DB_OPTS="${DB_OPTS} -DICE_DB_PASS=${ICE_DB_PASS:-icepass}"
export CATALINA_OPTS="${CATALINA_OPTS} ${DB_OPTS}"
