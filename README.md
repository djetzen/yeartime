# Yeartime

![Code Coverage](https://codecov.io/gh/djetzen/yeartime/branch/main/graphs/badge.svg?branch=main)

# Database scheme

## Activity

Here the legend is stored. This is simply an ID and a Name of the category.

## Day

A day consists of a date (e.g. 08.01.2021), a user (e.g. Karl) and 24 Hours.

## Hour

Every hour consists of a number (0-23) and a referenced activity.

# Local Development

start docker-compose in config folder login with  `psql --host=localhost --username=yeartime_user --dbname=yeartime`
