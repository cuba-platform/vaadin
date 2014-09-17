#!/bin/bash
ant clean && ant -Dvaadin.version=$1 -Dvaadin.maven.version=$1 && ant -f install.xml -Dvaadin.version=$1
