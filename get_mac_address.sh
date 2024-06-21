#!/bin/bash
#
# Script: get_mac_address.sh
# Description: Retrieves MAC address of a given IP using ARP
#

# Check if 'arp' command is available
if ! command -v arp &> /dev/null; then
    echo "Error: 'arp' command not found. Installing required package..."
    if [ -x "$(command -v apt)" ]; then
        sudo apt update
        sudo apt install -y net-tools
    elif [ -x "$(command -v yum)" ]; then
        sudo yum install -y net-tools
    else
        echo "Error: Package manager not found. Unable to install 'net-tools'."
        exit 1
    fi
fi

# Check if IP address is provided as argument
if [ -z "$1" ]; then
    echo "Usage: ./get_mac_address.sh <IP>"
    exit 1
fi

ip=$1

# Retrieve MAC address using ARP
mac_address=$(arp -a | awk '{print $4}')

if [ -z "$mac_address" ]; then
    echo "Error: MAC address not found for IP $ip"
    exit 1
fi

echo $mac_address