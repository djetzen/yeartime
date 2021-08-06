trigger build for change of sonarqube rules

#### CodeCoverage from CodeCov

![Code Coverage](https://codecov.io/gh/djetzen/yeartime/branch/main/graphs/badge.svg?branch=main)

#### SonarCloud analysis:

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=djetzen_yeartime&metric=bugs)](https://sonarcloud.io/dashboard?id=djetzen_yeartime)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=djetzen_yeartime&metric=code_smells)](https://sonarcloud.io/dashboard?id=djetzen_yeartime)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=djetzen_yeartime&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=djetzen_yeartime)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=djetzen_yeartime&metric=ncloc)](https://sonarcloud.io/dashboard?id=djetzen_yeartime)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=djetzen_yeartime&metric=sqale_index)](https://sonarcloud.io/dashboard?id=djetzen_yeartime)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=djetzen_yeartime&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=djetzen_yeartime)

# Yeartime

This repository contains a small backend for capturing what you have done on a specific day. I saw this kind of activity
tracking on reddit and started doing it in excel. As I am not very familiar with excel I decided to implement a small
backend, which has the same functionality. I also wanted to try some new things, with which I am not very familiar with.
In particular this was archunit, kotlin, github actions, flyway, codecov and kind of gradle.

The architecture used here is the onionarchitecture (or ports and adapters). The database scheme is a relational, as I
am quite familiar with this.

In the future I plan to develop a frontend as well and do some kind of statistics.

# Database scheme

## Activity

The legend is stored here. So each hour has an activity, the primary key is the name of this activity.

## Day

A day consists of a date (e.g. 08.01.2021), a user (e.g. Karl) and a list of 24 Hours.

## Hour

Every hour consists of a number (0-23) and a referenced activity.

# Local Development

start docker-compose in config folder to get a postgres up and running. You can then login
with  `psql --host=localhost --username=yeartime_user --dbname=yeartime`.

