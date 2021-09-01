#!/bin/bash

if docker pull kvalitetsit/vdx-example-java-documentation:latest; then
    echo "Copy from old documentation image."
    docker cp $(docker create kvalitetsit/vdx-example-java-documentation:latest):/usr/share/nginx/html target/old
fi
